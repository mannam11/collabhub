package com.tool.collabhub.repository;

import com.tool.collabhub.model.DeveloperProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DeveloperProfileRepository extends MongoRepository<DeveloperProfile,String> {
    Optional<DeveloperProfile> findByUserId(String userId);
}
