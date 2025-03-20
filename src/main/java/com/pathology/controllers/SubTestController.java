package com.pathology.controllers;

import com.pathology.Services.SubTestService;
import com.pathology.entities.SubTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubTestController {
    @Autowired
    private SubTestService subTestService;

    @PostMapping("/subtest")
    public ResponseEntity<SubTest> create (@RequestBody SubTest subTest){
        return ResponseEntity.status(HttpStatus.CREATED).body(subTestService.create(subTest));
    }

    @GetMapping("/subtest")
    public ResponseEntity<List<SubTest>> getAll(){
        return ResponseEntity.ok(subTestService.getAll());
    }

    @PostMapping("/subtest/{subTestId}")
    public ResponseEntity<SubTest> getById(@PathVariable String subTestId){
        return ResponseEntity.status(HttpStatus.OK).body(subTestService.getById(subTestId));
    }

    @GetMapping("/subtest/setValue/{subTestId}/{updatedValue}")
    public ResponseEntity<Map<String, String>> updateValue(@PathVariable String subTestId, @PathVariable String updatedValue){
        subTestService.update(subTestId,updatedValue);
//        return ResponseEntity.ok("updated");
        Map<String, String> response = new HashMap<>();
        response.put("message", "SubTest value updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{subTestId}")
    public ResponseEntity<String> deleteSubTest(@PathVariable String subTestId){
        subTestService.delete(subTestId);
        return ResponseEntity.ok("Deleted");
    }

}
