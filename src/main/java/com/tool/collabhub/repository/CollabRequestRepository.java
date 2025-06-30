package com.tool.collabhub.repository;

import com.tool.collabhub.model.CollabRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CollabRequestRepository extends MongoRepository<CollabRequest,String> {
    Optional<CollabRequest> findTopByRequestedUserInfo_RequestedUserIdAndProjectInfo_ProjectIdOrderByRequestedAtDesc(String userId, String projectId);
    List<CollabRequest> findByRequestedUserInfo_RequestedUserId(String requestedUserId);
    List<CollabRequest> findByProjectInfo_ProjectId(String projectId);
}
