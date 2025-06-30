package com.tool.collabhub.service;

import com.tool.collabhub.dto.request.ProjectCollabRequest;
import com.tool.collabhub.dto.response.CollabReqResponse;

import java.util.List;

public interface CollabRequestService {
    public void sendRequest(String projectId,ProjectCollabRequest request);
    public List<CollabReqResponse> getIndividualCollabRequests();
    public List<CollabReqResponse> getProjectWiseCollabRequest(String projectId);
}
