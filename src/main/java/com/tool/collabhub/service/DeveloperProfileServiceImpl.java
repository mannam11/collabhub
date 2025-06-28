package com.tool.collabhub.service;

import com.tool.collabhub.auth.utils.AuthUtils;
import com.tool.collabhub.dto.request.DeveloperProfileRequest;
import com.tool.collabhub.dto.response.DeveloperProfileResponse;
import com.tool.collabhub.exception.InvalidDeveloperProfileRequestException;
import com.tool.collabhub.mapper.DeveloperProfileResponseMapper;
import com.tool.collabhub.model.DeveloperProfile;
import com.tool.collabhub.repository.DeveloperProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeveloperProfileServiceImpl implements DeveloperProfileService{

    private final DeveloperProfileRepository developerProfileRepository;
    private final SkillService skillService;

    @Override
    public DeveloperProfileResponse createAndSave(DeveloperProfileRequest request) {

        String currentUserId = AuthUtils.getCurrentUserId();

        log.info("Creating developer profile for user {}", currentUserId);

        Optional<DeveloperProfile> optionalDeveloperProfile = developerProfileRepository.findByUserId(currentUserId);
        if(optionalDeveloperProfile.isPresent()){
            throw new InvalidDeveloperProfileRequestException( "Developer profile already exists");
        }

        DeveloperProfileRequest.validate(request);

        List<String> skillsWithName = skillService.getSkillsWithName(request.getSkillIds());

        DeveloperProfile profile = DeveloperProfile.builder()
                .title(request.getTitle())
                .bio(request.getBio())
                .userId(currentUserId)
                .experienceInYears(request.getExperienceInYears())
                .skillIds(request.getSkillIds())
                .githubUrl(request.getGithubUrl())
                .linkedinUrl(request.getLinkedinUrl())
                .createdOn(LocalDateTime.now())
                .build();

        boolean isDeveloperProfileComplete = isProfileComplete(profile);

        profile.setProfileComplete(isDeveloperProfileComplete);

        DeveloperProfile savedProfile = save(profile);
        log.info("Developer profile created for user {}", currentUserId);

        return DeveloperProfileResponseMapper
                .mapToResponse(savedProfile,skillsWithName);
    }

    @Override
    public DeveloperProfile save(DeveloperProfile developerProfile) {
        return developerProfileRepository.save(developerProfile);
    }

    @Override
    public List<DeveloperProfileResponse> getAll() {
       List<DeveloperProfile> developerProfileList = developerProfileRepository.findAll();

       List<DeveloperProfileResponse> responseList = new ArrayList<>();
       for(DeveloperProfile profile : developerProfileList){
           List<String> skillsWithName = skillService.getSkillsWithName(profile.getSkillIds());

           responseList.add(DeveloperProfileResponseMapper.mapToResponse(profile,skillsWithName));
       }

       return responseList;
    }

    private boolean isProfileComplete(DeveloperProfile profile) {
        return StringUtils.hasText(profile.getTitle())
                && StringUtils.hasText(profile.getBio()) && profile.getBio().length() >= 30
                && profile.getSkillIds() != null && !profile.getSkillIds().isEmpty()
                && profile.getExperienceInYears() != null && profile.getExperienceInYears() >= 0
                && (StringUtils.hasText(profile.getGithubUrl()) || StringUtils.hasText(profile.getLinkedinUrl()));
    }

}
