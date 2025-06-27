package com.tool.collabhub.controller;

import com.tool.collabhub.elasticsearch.service.ProjectIndexSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final ProjectIndexSearchService projectIndexSearchService;

    @GetMapping
    public ResponseEntity<?> search(@RequestParam("key") String keyword,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(projectIndexSearchService.search(keyword, page, size));
    }

}
