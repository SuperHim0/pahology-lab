package com.pathology.Services.impl;

import com.pathology.Services.SubTestService;
import com.pathology.Services.TestService;
import com.pathology.entities.SubTest;
import com.pathology.entities.Test;
import com.pathology.exception.ResourceNotFoundException;
import com.pathology.repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {
    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private SubTestService subTestService;


    @Override
    public Test create(Test test) {
        String randomId = UUID.randomUUID().toString();
        test.setTestId(randomId);
        return testRepository.save(test);
    }

    @Override
    public List<Test> getAll() {
        return testRepository.findAll();
    }

    @Override
    public Test getById(String testId) {
        Test testDetails = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("the given id is not available on server"));
        String testId1 = testDetails.getTestId();
        List<SubTest> findByAllDetails = subTestService.findByTestId(testId1);
        log.info("details of subtest:{}",findByAllDetails);
        testDetails.setSubTests(findByAllDetails);
//        generateBillingPdf.generateCbcReport(HttpServletResponse, testDetails);

        return testDetails;
    }

    @Override
    public void deleteTest(String testId) {
         testRepository.deleteById(testId);
        System.out.println("test delete success: 102 :"+testId);
    }

    @Override
    public void updateTestRate(String testId,String rate) {

        Optional<Test> testDetail = testRepository.findById(testId);
        testDetail.ifPresent(test -> {
            test.setRate(rate);
        });

//        return testRepository.;
    }

    /**@Override
    public void  generatePdfOfTest(HttpServletResponse response, String testId) {
        Test testDetails = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("the given id is not available on server"));
        String testId1 = testDetails.getTestId();
        List<SubTest> findByAllDetails = subTestService.findByTestId(testId1);
        log.info("details of subtest:{}",findByAllDetails);
        testDetails.setSubTests(findByAllDetails);
//        generateBillingPdf.generateCbcReport(response, testDetails);

//        Class<GenerateBillingPdf> generateBillingPdfClass = GenerateBillingPdf.class;

        for (SubTest subTest : findByAllDetails){
            System.out.println(subTest);
        }

    }
     */

    @Override
    public List<Test> findTestByPatient(String patientId) {
        List<Test> listTests = testRepository.findTestByPatientId(patientId);
        return listTests;
    }

    @Override
    public void updatePatientIdIntoTest(String patientId, String testId) {

        Test test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("test not found for given TestId"));
        test.setPatientId(patientId);
        testRepository.save(test);
        log.info("update patientId:{}",test.getPatientId());
    }
}
