package com.tool.collabhub.service;

import com.tool.collabhub.dto.request.DeveloperProfileRequest;
import com.tool.collabhub.dto.response.DeveloperProfileResponse;
import com.tool.collabhub.model.DeveloperProfile;

import java.util.List;

public interface DeveloperProfileService {
    public DeveloperProfileResponse createAndSave(DeveloperProfileRequest request);
    public DeveloperProfile save(DeveloperProfile developerProfile);
    public List<DeveloperProfileResponse> getAll();
}
