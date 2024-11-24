package com.pathology.controllers;

import com.pathology.Services.SubTestService;
import com.pathology.entities.SubTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subtest")
public class SubTestController {
    @Autowired
    private SubTestService subTestService;

    @PostMapping
    public ResponseEntity<SubTest> create (@RequestBody SubTest subTest){
        return ResponseEntity.status(HttpStatus.CREATED).body(subTestService.create(subTest));
    }

    @GetMapping
    public ResponseEntity<List<SubTest>> getAll(){
        return ResponseEntity.ok(subTestService.getAll());
    }

    @PostMapping("/{subTestId}")
    public ResponseEntity<SubTest> getById(@PathVariable String subTestId){
        return ResponseEntity.status(HttpStatus.OK).body(subTestService.getById(subTestId));
    }

    @GetMapping("setValue/{subTestId}/{updatedValue}")
    public ResponseEntity<String> updateValue(@PathVariable String subTestId, @PathVariable String updatedValue){
        subTestService.update(subTestId,updatedValue);
        return ResponseEntity.ok("updated");
    }

}
