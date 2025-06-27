package com.tool.collabhub.dto.response;

import com.tool.collabhub.model.DeveloperProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperProfileResponse {
    private String id;
    private String title;
    private Integer experienceInYears;
    private String bio;
    private List<String> skills;
    private String githubUrl;
    private String linkedinUrl;
    private boolean isProfileComplete;

    public static DeveloperProfileResponse mapToResponse(DeveloperProfile profile,List<String> skills){
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
