package com.tool.collabhub.service;

import com.tool.collabhub.dto.request.ProjectCollabRequest;

public interface CollabRequestService {
    public void sendRequest(String projectId,ProjectCollabRequest request);
}
