package com.tool.collabhub.repository;

import com.tool.collabhub.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends MongoRepository<Skill,String> {
    List<Skill> findAllByIdIn(List<String> ids);
    Optional<Skill> findByName(String name);
}
