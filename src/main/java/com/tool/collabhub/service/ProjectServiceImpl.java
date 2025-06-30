package com.tool.collabhub.service;

import com.tool.collabhub.dto.request.ProjectRequest;
import com.tool.collabhub.dto.response.ProjectResponse;
import com.tool.collabhub.elasticsearch.service.ProjectIndexService;
import com.tool.collabhub.exception.ProjectException;
import com.tool.collabhub.exception.ProjectNotFoundException;
import com.tool.collabhub.mapper.ProjectResponseMapper;
import com.tool.collabhub.model.Project;
import com.tool.collabhub.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final ProjectIndexService projectIndexService;

    @Override
    public ProjectResponse createAndSave(String userId, ProjectRequest request) {

        validateUser(userId);

        ProjectRequest.validate(request);

        Project newProject = build(userId, request);

        save(newProject);
        log.info("Project created for user with id : {} and project Id : {}",userId,newProject);

        projectIndexService.save(newProject);
        log.info("Project index created with id : {}", newProject.getId());

        return ProjectResponseMapper.from(newProject);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    private static void validateUser(String userId) {
        if(userId == null || userId.trim().isEmpty()){
            throw new ProjectException("Invalid user");
        }
    }

    private Project build(String userId, ProjectRequest request) {
        return Project.builder()
                .id(UUID.randomUUID().toString())
                .title(request.getTitle())
                .description(request.getDescription())
                .techStack(request.getTechStack())
                .userId(userId)
                .createdOn(LocalDateTime.now())
                .createdBy(userId)
                .build();
    }

    @Override
    public ProjectResponse getById(String userId,String projectId) {
        List<Project> projects = findAllByUserId(userId);

        return projects.stream()
                .filter(project -> project.getId().equals(projectId))
                .map(ProjectResponseMapper::from)
                .findFirst()
                .orElseThrow(() ->new ProjectNotFoundException("Project not found with id : " + projectId));
    }

    @Override
    public List<ProjectResponse> getAll(String userId) {

        return findAllByUserId(userId).stream()
                .map(ProjectResponseMapper::from)
                .toList();
    }

    @Override
    public void deleteById(String userId, String projectId) {
        Project project = getProject(userId, projectId);

        if(project == null){
            throw new ProjectException("Project not found");
        }

        projectRepository.delete(project);

        log.info("Project with id : {} deleted successfully",project.getId());
    }

    private Project getProject(String userId, String projectId) {
        List<Project> projects = findAllByUserId(userId);

        return projects.stream()
                .filter(project -> project.getId().equals(projectId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Project> findAllByUserId(String userId) {
        return projectRepository.findAllByUserId(userId);
    }

    @Override
    public void update(String userId, String projectId, ProjectRequest request) {

        validateUser(userId);

        ProjectRequest.validate(request);

        Project project = getProject(userId,projectId);

        if(project == null){
            throw new ProjectException("Project not found with id : " + projectId);
        }

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setTechStack(request.getTechStack());
        project.setUpdatedOn(LocalDateTime.now());
        project.setUpdatedBy(userId);

        projectRepository.save(project);

        log.info("Project with id : {} updated successfully", project.getId());
    }

    @Override
    public Project findById(String projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()){
            throw new ProjectNotFoundException("Project with id  : " + projectId + " not found");
        }

        return optionalProject.get();
    }
}
