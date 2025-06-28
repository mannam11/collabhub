package com.tool.collabhub.mapper;

import com.tool.collabhub.dto.response.DeveloperProfileResponse;
import com.tool.collabhub.model.DeveloperProfile;

import java.util.List;

public class DeveloperProfileResponseMapper {

    public static DeveloperProfileResponse mapToResponse(DeveloperProfile profile, List<String> skills){
        return DeveloperProfileResponse.builder()
                .id(profile.getId())
                .bio(profile.getBio())
                .title(profile.getTitle())
                .skills(skills)
                .experienceInYears(profile.getExperienceInYears())
                .githubUrl(profile.getGithubUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .isProfileComplete(profile.isProfileComplete())
                .build();
    }
}
