package com.tool.collabhub.mapper;

import com.tool.collabhub.dto.response.ProjectResponse;
import com.tool.collabhub.elasticsearch.model.ProjectIndex;
import com.tool.collabhub.model.Project;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectResponseMapper {

    public static ProjectResponse from(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .createdBy(project.getCreatedBy())
                .createdOn(project.getCreatedOn())
                .build();
    }

    public static ProjectResponse from(ProjectIndex projectIndex){
        return ProjectResponse.builder()
                .id(projectIndex.getId())
                .title(projectIndex.getTitle())
                .description(projectIndex.getDescription())
                .createdBy(projectIndex.getCreatedBy())
                .createdOn(projectIndex.getCreatedOn())
                .build();
    }

}
