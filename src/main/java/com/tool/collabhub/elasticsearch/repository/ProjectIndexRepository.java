package com.tool.collabhub.elasticsearch.repository;

import com.tool.collabhub.elasticsearch.model.ProjectIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProjectIndexRepository extends ElasticsearchRepository<ProjectIndex,String> {
}
