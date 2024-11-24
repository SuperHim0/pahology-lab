package com.pathology.controllers;

import com.pathology.Services.TestService;
import com.pathology.Services.impl.TestServiceImpl;
import com.pathology.entities.Test;
import com.pathology.repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping
    public ResponseEntity<Test> create (@RequestBody Test  test){
        logger.info("controller called to create test info:");
        System.out.println(test.getTestName()+" -- > "+test.getRate());
        return ResponseEntity.status(HttpStatus.CREATED).body(testService.create(test));
    }

    @GetMapping
    public ResponseEntity<List<Test>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(testService.getAll());
    }

    @PostMapping("/{testId}")
    public ResponseEntity<Test> getById(@PathVariable String testId){
        return ResponseEntity.ok(testService.getById(testId));
    }


    @GetMapping("setTest/{patientId}/{testId}")
    public ResponseEntity<String> setTestToPatient(@PathVariable String patientId, @PathVariable String testId){
        testService.updatePatientIdIntoTest(patientId,testId);
        return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body("Added");
    }

}
