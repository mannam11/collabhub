package com.tool.collabhub.mapper;

import com.tool.collabhub.dto.response.CollabReqResponse;
import com.tool.collabhub.model.CollabRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollabReqResponseMapper {

    public static CollabReqResponse mapToResponse(CollabRequest request){

        return CollabReqResponse.builder()
                .collabRequestId(request.getId())
                .status(request.getStatus().name())
                .requestedOn(request.getRequestedAt())
                .updatedOn(request.getUpdatedAt())
                .projectInfo(CollabReqResponse.ProjectInfo.builder()
                        .projectId(request.getProjectInfo().getProjectId())
                        .projectName(request.getProjectInfo().getTitle())
                        .build())
                .build();
    }
}
