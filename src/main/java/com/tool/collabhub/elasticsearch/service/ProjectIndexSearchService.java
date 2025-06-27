package com.tool.collabhub.elasticsearch.service;

import com.tool.collabhub.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectIndexSearchService {
    public List<ProjectResponse> search(String keyword, int page, int size);
}
