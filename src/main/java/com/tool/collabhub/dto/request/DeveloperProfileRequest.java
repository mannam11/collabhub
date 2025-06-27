package com.tool.collabhub.dto.request;

import com.tool.collabhub.exception.InvalidDeveloperProfileRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperProfileRequest {
    private String title;
    private Integer experienceInYears;
    private String bio;
    private List<String> skillIds;
    private String githubUrl;
    private String linkedinUrl;

    public static void validate(DeveloperProfileRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new InvalidDeveloperProfileRequestException("Title must not be empty");
        }

        if (request.getExperienceInYears() == null || request.getExperienceInYears() < 0) {
            throw new InvalidDeveloperProfileRequestException("Experience years must be a non-negative integer");
        }

        if (request.getBio() == null || request.getBio().trim().length() < 30) {
            throw new InvalidDeveloperProfileRequestException("Bio must be at least 30 characters long");
        }

        if (request.getSkillIds() == null || request.getSkillIds().isEmpty()) {
            throw new InvalidDeveloperProfileRequestException("At least one skill must be selected");
        }

        if ((request.getGithubUrl() == null || request.getGithubUrl().trim().isEmpty())
                && (request.getLinkedinUrl() == null || request.getLinkedinUrl().trim().isEmpty())) {
            throw new InvalidDeveloperProfileRequestException("Either GitHub or LinkedIn URL must be provided");
        }
    }
}
