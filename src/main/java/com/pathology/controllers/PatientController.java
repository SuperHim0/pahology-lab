package com.pathology.controllers;

import com.pathology.Services.PatientService;
import com.pathology.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    //create patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(patient));
    }
    //getall patient
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatient(){
        return ResponseEntity.ok(patientService.getAll());
    }

    //getby id
    @PostMapping("/{patientId}")
    public ResponseEntity<Patient> getById(@PathVariable String patientId){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getByPatientId(patientId));
    }

    //delete

    //update

    //any other
}
