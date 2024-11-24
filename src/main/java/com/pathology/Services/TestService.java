package com.pathology.Services;

import com.pathology.entities.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface TestService {

    //save
    Test create(Test test);

    //get all
    List<Test> getAll();

    //get by id
    Test getById(String testId);

    //delete
    void deleteTest(String testId);

    //update
    void updateTestRate(String testId,String rate);


//    void generatePdfOfTest(HttpServletResponse response,String testId);

    List<Test> findTestByPatient(String PatientId);

    //
    void updatePatientIdIntoTest(String patientId,String testId);


}
