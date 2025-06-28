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
}
