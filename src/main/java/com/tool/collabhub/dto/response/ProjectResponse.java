package com.tool.collabhub.dto.response;

import com.tool.collabhub.elasticsearch.model.ProjectIndex;
import com.tool.collabhub.model.Project;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponse {

    private String id;
    private String title;
    private String description;
    private LocalDateTime createdOn;
    private String createdBy;

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
