package com.tool.collabhub.model;

import com.tool.collabhub.enums.CollabRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class CollabRequest {

    @Id
    private String id;
    private CollabRequestStatus status;
    private String message;
    private ProjectInfo projectInfo;
    private RequestedUserInfo requestedUserInfo;
    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectInfo{
        private String projectId;
        private String title;
        private String projectOwnerId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestedUserInfo{
        private String requestedUserId;
    }
}
