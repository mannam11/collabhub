package com.tool.collabhub.controller;

import com.tool.collabhub.dto.request.DeveloperProfileRequest;
import com.tool.collabhub.service.DeveloperProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/developer-profile")
@RequiredArgsConstructor
public class DeveloperProfileController {

    private final DeveloperProfileService developerProfileService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DeveloperProfileRequest request){
        return new ResponseEntity<>(developerProfileService.createAndSave(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(developerProfileService.getAll());
    }

}
