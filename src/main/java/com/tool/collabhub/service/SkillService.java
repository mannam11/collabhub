package com.tool.collabhub.service;

import com.tool.collabhub.dto.request.SkillRequest;
import com.tool.collabhub.dto.response.SkillResponse;
import com.tool.collabhub.model.Skill;

import java.util.List;

public interface SkillService {
    public void createAndSave(SkillRequest request);
    public void save(Skill skill);
    public List<Skill> findAllByIdIn(List<String> skillIds);
    public List<String> getSkillsWithName(List<String> skillIds);
    public List<SkillResponse> getAll();
}
