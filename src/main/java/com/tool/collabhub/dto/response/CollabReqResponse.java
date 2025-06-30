package com.tool.collabhub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollabReqResponse {

    private String collabRequestId;
    private ProjectInfo projectInfo;
    private String status;
    private LocalDateTime requestedOn;
    private LocalDateTime updatedOn;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProjectInfo{
        private String projectId;
        private String projectName;
    }
}
