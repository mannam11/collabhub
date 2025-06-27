package com.tool.collabhub.elasticsearch.service;

import com.tool.collabhub.dto.response.ProjectResponse;
import com.tool.collabhub.elasticsearch.model.ProjectIndex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectIndexSearchServiceImpl implements ProjectIndexSearchService{

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<ProjectResponse> search(String keyword,int page, int size) {

        if(keyword == null || keyword.trim().isEmpty()){
            return List.of();
        }
        log.info("Search keyword id : {}",keyword);

        Pageable pageable = PageRequest.of(page,size);
        log.info("Getting page : {}, with size : {}",page,size);

        try {
            NativeQuery query = NativeQuery.builder()
                    .withQuery(q -> q
                            .multiMatch(m -> m
                                    .query(keyword)
                                    .fields("title", "description")
                                    .fuzziness("AUTO")
                            )
                    )
                    .withPageable(pageable)
                    .build();

            SearchHits<ProjectIndex> hits = elasticsearchOperations.search(query, ProjectIndex.class);

            return hits.getSearchHits().stream()
                    .map(SearchHit::getContent)
                    .map(ProjectResponse::from)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error during fuzzy search", e);
            return List.of();
        }
    }
}
