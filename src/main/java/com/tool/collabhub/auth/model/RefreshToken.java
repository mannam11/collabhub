package com.tool.collabhub.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshToken {

    @Id
    private String id;
    private String refreshToken;
    private String userId;
    private LocalDateTime createdOn;
    private LocalDateTime expiresAt;
}
