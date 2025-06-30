package com.tool.collabhub.service;

import com.tool.collabhub.auth.utils.AuthUtils;
import com.tool.collabhub.dto.request.ProjectCollabRequest;
import com.tool.collabhub.dto.response.CollabReqResponse;
import com.tool.collabhub.enums.CollabRequestStatus;
import com.tool.collabhub.exception.InvalidProjectCollabRequestException;
import com.tool.collabhub.mapper.CollabReqResponseMapper;
import com.tool.collabhub.model.CollabRequest;
import com.tool.collabhub.model.Project;
import com.tool.collabhub.repository.CollabRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollabRequestServiceImpl implements CollabRequestService{

    private static final int coolingOffPeriodInDays = 30;

    private final CollabRequestRepository collabRequestRepository;
    private final ProjectService projectService;

    @Override
    public void sendRequest(String projectId, ProjectCollabRequest request) {
        if (projectId == null || projectId.trim().isEmpty()) {
            throw new InvalidProjectCollabRequestException("Project ID cannot be null or empty.");
        }

        Project project = projectService.findById(projectId);

        String currentUserId = AuthUtils.getCurrentUserId();

        if (project.getUserId().equals(currentUserId)) {
            throw new InvalidProjectCollabRequestException("You cannot request to collaborate on your own project.");
        }

        Optional<CollabRequest> optionalCollabRequest = collabRequestRepository
                .findTopByRequestedUserInfo_RequestedUserIdAndProjectInfo_ProjectIdOrderByRequestedAtDesc(currentUserId,projectId);

        validateRequestEligibility(optionalCollabRequest);

        CollabRequest collabRequest = build(request, currentUserId, project);

        collabRequestRepository.save(collabRequest);
        log.info("CollabRequest created for user : {} for the project id : {}",currentUserId,project.getId());
    }

    @Override
    public List<CollabReqResponse> getIndividualCollabRequests() {
        String currentUserId = AuthUtils.getCurrentUserId();

        List<CollabRequest> collabRequestList = collabRequestRepository.findByRequestedUserInfo_RequestedUserId(currentUserId);

        log.info("Found : {} collab request for user with id : {}",collabRequestList.size(),currentUserId);

        return collabRequestList.stream()
                .map(CollabReqResponseMapper::mapToResponse)
                .toList();
    }

    @Override
    public List<CollabReqResponse> getProjectWiseCollabRequests(String projectId) {

        if(projectId == null || projectId.trim().isEmpty()){
            throw new InvalidProjectCollabRequestException("Project id must not be null or empty");
        }

        Project project = projectService.findById(projectId);

        List<CollabRequest> collabRequestList = collabRequestRepository.findByProjectInfo_ProjectId(projectId);

        log.info("Found : {} collab request for project with id : {}",collabRequestList.size(),project.getId());

        return collabRequestList.stream()
                .map(CollabReqResponseMapper::mapToResponse)
                .toList();
    }

    private CollabRequest build(ProjectCollabRequest request, String currentUserId, Project project) {
        return CollabRequest.builder()
                .message(request.getMessage())
                .status(CollabRequestStatus.PENDING)
                .requestedAt(LocalDateTime.now())
                .requestedUserInfo(
                        CollabRequest.RequestedUserInfo.builder()
                                .requestedUserId(currentUserId)
                                .build()
                )
                .projectInfo(
                        CollabRequest.ProjectInfo.builder()
                                .projectId(project.getId())
                                .title(project.getTitle())
                                .projectOwnerId(project.getUserId())
                                .build()
                )
                .build();
    }

    private void validateRequestEligibility(Optional<CollabRequest> optionalCollabRequest) {
        if (optionalCollabRequest.isPresent()) {
            CollabRequest latest = optionalCollabRequest.get();
            CollabRequestStatus status = latest.getStatus();

            if (status == CollabRequestStatus.ACCEPTED || status == CollabRequestStatus.PENDING) {
                throw new InvalidProjectCollabRequestException("You already have an active request for this project.");
            }

            if (status == CollabRequestStatus.REJECTED) {
                LocalDateTime rejectedAt = latest.getUpdatedAt();
                LocalDateTime cooldownThreshold = rejectedAt.plusDays(coolingOffPeriodInDays);
                if (cooldownThreshold.isAfter(LocalDateTime.now())) {
                    throw new InvalidProjectCollabRequestException("You must wait " + coolingOffPeriodInDays+" days before sending a new request.");
                }
            }
        }
    }
}
