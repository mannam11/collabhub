package com.tool.collabhub.controller;

import com.tool.collabhub.auth.repository.UserRepository;
import com.tool.collabhub.auth.utils.AuthUtils;
import com.tool.collabhub.dto.request.ProjectRequest;
import com.tool.collabhub.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectRequest request){
        return new ResponseEntity<>(projectService.createAndSave(AuthUtils.getCurrentUserId(),request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.getById(AuthUtils.getCurrentUserId(),projectId));
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(projectService.getAll(AuthUtils.getCurrentUserId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String projectId){
        projectService.deleteById(AuthUtils.getCurrentUserId(),projectId);
        return ResponseEntity.ok("Project with id : " + projectId + "deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String projectId,
                                    @RequestBody ProjectRequest request) {

        projectService.update(AuthUtils.getCurrentUserId(),projectId,request);
        return ResponseEntity.ok("Project updated successfully");
    }

}
