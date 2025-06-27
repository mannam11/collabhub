package com.tool.collabhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class DeveloperProfile {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;
    private String title;
    private Integer experienceInYears;
    private String bio;
    private List<String> skillIds;
    private String githubUrl;
    private String linkedinUrl;
    private boolean isProfileComplete;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
