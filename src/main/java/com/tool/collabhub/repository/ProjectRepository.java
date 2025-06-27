package com.tool.collabhub.repository;

import com.tool.collabhub.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project,String> {
    public List<Project> findAllByUserId(String userId);
}
