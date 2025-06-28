package com.tool.collabhub.mapper;

import com.tool.collabhub.dto.response.SkillResponse;
import com.tool.collabhub.model.Skill;

public class SkillResponseMapper {
    public static SkillResponse mapToResponse(Skill skill){
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
