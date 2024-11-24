package com.pathology.Services.impl;

import com.pathology.Services.PatientService;
import com.pathology.Services.SubTestService;
import com.pathology.Services.TestService;
import com.pathology.entities.Patient;
import com.pathology.entities.SubTest;
import com.pathology.entities.Test;
import com.pathology.exception.ResourceNotFoundException;
import com.pathology.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TestService testService;

    @Autowired
    private SubTestService subTestService;



//    public PatientServiceImpl(PatientRepository patientRepository){
//        this.patientRepository = patientRepository;
//    }


    @Override
    public Patient create(Patient patient) {
        String patientId = UUID.randomUUID().toString();
        LocalDateTime ldt = LocalDateTime.now();
        log.info("reporting data is : {}",ldt);
        String strDate = ldt.toString();
        String[] dateTime = strDate.split("T");
        patient.setReportingDate(dateTime[0]+" : "+dateTime[1]);
        
        patient.setPatientId(patientId);
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getByPatientId(String patientId) {
//        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found with the given id"));
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient Not Found By the Given Id"));
        String patientId1 = patient.getPatientId();
        List<Test> listTest = testService.findTestByPatient(patientId);
        patient.setTest(listTest);
        for (Test test : listTest){
            String testId = test.getTestId();
            List<SubTest> subTestList = subTestService.findByTestId(testId);
            test.setSubTests(subTestList);
        }
        return patient;
    }

    @Override
    public void updatePatient(Patient patient){
//        return patientRepository.updatePatientByPatientId(patient);
        String patientId = patient.getPatientId();
        Optional<Patient> patientDetails = patientRepository.findById(patientId);
        patientDetails.ifPresent(patient1 -> {
            patient1.setAge(patient.getAge());
        });

    }

    @Override
    public void deletePatient(String patientId) {
        patientRepository.deleteById(patientId);

    }

    @Override
    public void getTestDetailsByPatientId(String patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient Not Found By the Given Id"));
        String patientId1 = patient.getPatientId();
        List<Test> listTest = testService.findTestByPatient(patientId);
        patient.setTest(listTest);
        for (Test test : listTest){
            String testId = test.getTestId();
            List<SubTest> subTestList = subTestService.findByTestId(testId);
            test.setSubTests(subTestList);
        }
    }
}
