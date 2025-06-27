package com.tool.collabhub.controller;

import com.tool.collabhub.dto.request.ProjectCollabRequest;
import com.tool.collabhub.service.CollabRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collab-request")
public class CollabRequestController {

    private final CollabRequestService collabRequestService;

    @PostMapping("/{projectId}")
    public ResponseEntity<?> sendRequest(@PathVariable String projectId,
                                         @RequestBody ProjectCollabRequest request){
        collabRequestService.sendRequest(projectId,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
