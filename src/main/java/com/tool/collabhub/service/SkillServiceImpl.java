package com.tool.collabhub.service;

import com.tool.collabhub.auth.utils.AuthUtils;
import com.tool.collabhub.dto.request.SkillRequest;
import com.tool.collabhub.dto.response.SkillResponse;
import com.tool.collabhub.enums.SkillType;
import com.tool.collabhub.exception.InvalidDeveloperProfileRequestException;
import com.tool.collabhub.exception.InvalidSkillRequestException;
import com.tool.collabhub.model.Skill;
import com.tool.collabhub.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillServiceImpl implements SkillService{

    private final SkillRepository skillRepository;

    @Override
    public void createAndSave(SkillRequest request) {
        SkillRequest.validate(request);

        Optional<Skill> optionalSkill = skillRepository.findByName(request.getName());
        if(optionalSkill.isPresent()){
            throw new InvalidSkillRequestException("Skill with name " + request.getName() + " already exists");
        }

        Skill newSkill = Skill.builder()
                .name(request.getName())
                .skillType(SkillType.fromValue(request.getSkillType()))
                .createdOn(LocalDateTime.now())
                .createdBy(AuthUtils.getCurrentUserId())
                .build();

        save(newSkill);
    }

    @Override
    public void save(Skill skill) {
        skillRepository.save(skill);
        log.info("Skill with id : {} saved successfully",skill.getId());
    }

    @Override
    public List<Skill> findAllByIdIn(List<String> skillIds) {
        return skillRepository.findAllByIdIn(skillIds);
    }

    @Override
    public List<String> getSkillsWithName(List<String> skillIds) {
        List<Skill> skills =  findAllByIdIn(skillIds);

        if(skillIds.size() != skills.size()){
            throw new InvalidDeveloperProfileRequestException("One or more skills are invalid");
        }

        return skills.stream()
                .map(Skill::getName)
                .toList();
    }

    @Override
    public List<SkillResponse> getAll() {
        return skillRepository.findAll()
                .stream()
                .map(SkillResponse::mapToResponse)
                .toList();
    }


}
