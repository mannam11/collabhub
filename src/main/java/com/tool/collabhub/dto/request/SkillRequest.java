package com.tool.collabhub.dto.request;

import com.tool.collabhub.exception.InvalidSkillRequestException;
import lombok.Data;

@Data
public class SkillRequest {
    private String name;
    private String skillType;

    public static void validate(SkillRequest request){
        if(request.getName() == null || request.getName().trim().isEmpty()){
            throw new InvalidSkillRequestException("Skill name should not be empty");
        }

        if(request.getSkillType() == null || request.getSkillType().trim().isEmpty()){
            throw new InvalidSkillRequestException("Skill type should not be empty");
        }
    }
}
