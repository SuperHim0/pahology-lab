package com.pathology.repository;

import com.pathology.entities.Patient;
import com.pathology.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,String> {
    //any costume query

    //update rate
//    @Transactional
//    @Modifying
//    @Query("update test rate set id=:id")
//    int updateTestRate(String id);

    List<Test> findTestByPatientId(String patientId);



}
