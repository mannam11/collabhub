package com.tool.collabhub.dto.response;

import com.tool.collabhub.model.Skill;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillResponse {
    private String id;
    private String name;

    public static SkillResponse mapToResponse(Skill skill){
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
