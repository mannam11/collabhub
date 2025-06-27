package com.tool.collabhub.dto.request;

import com.tool.collabhub.exception.InvalidProjectCreationRequestException;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {

    private String title;
    private String description;
    private List<String> techStack;

    public static void validate(ProjectRequest request){
        if(request.getTitle() == null || request.getTitle().trim().isEmpty()){
            throw new InvalidProjectCreationRequestException("Title should not be empty");
        }

        if(request.getDescription() == null || request.getDescription().trim() .isEmpty()){
            throw new InvalidProjectCreationRequestException("Description should not be empty");
        }

        if(request.getTechStack() == null || request.getTechStack().isEmpty()){
            throw new InvalidProjectCreationRequestException("TechStack should not be empty");
        }
    }
}
