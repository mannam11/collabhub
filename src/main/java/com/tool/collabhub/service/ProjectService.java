package com.tool.collabhub.service;

import com.tool.collabhub.dto.request.ProjectRequest;
import com.tool.collabhub.dto.response.ProjectResponse;
import com.tool.collabhub.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    public ProjectResponse createAndSave(String userId, ProjectRequest request);
    public void save(Project project);
    public ProjectResponse getById(String userId,String projectId);
    public List<ProjectResponse> getAll(String userId);
    public void deleteById(String userId, String projectId);
    public List<Project> findAllByUserId(String userId);
    public void update(String userId, String projectId, ProjectRequest request);
    public Optional<Project> findById(String projectId);
}
