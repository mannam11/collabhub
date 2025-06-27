package com.tool.collabhub.elasticsearch.service;

import com.tool.collabhub.elasticsearch.model.ProjectIndex;
import com.tool.collabhub.elasticsearch.repository.ProjectIndexRepository;
import com.tool.collabhub.model.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectIndexServiceImpl implements ProjectIndexService{

    private final ProjectIndexRepository projectIndexRepository;

    @Override
    public void save(Project project) {

        ProjectIndex projectIndex = build(project);

        projectIndexRepository.save(projectIndex);

        log.info("project_index created with id : {}", projectIndex.getId());
    }

    private ProjectIndex build(Project project){
        return ProjectIndex.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .userId(project.getUserId())
                .techStack(project.getTechStack())
                .createdOn(project.getCreatedOn())
                .createdBy(project.getCreatedBy())
                .updatedBy(project.getUpdatedBy())
                .updatedOn(project.getUpdatedOn())
                .build();
    }
}
