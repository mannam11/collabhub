package com.tool.collabhub.controller;

import com.tool.collabhub.dto.request.SkillRequest;
import com.tool.collabhub.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SkillRequest request){
        skillService.createAndSave(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(skillService.getAll());
    }
}
