package com.pathology.repository;

import com.pathology.entities.SubTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTestRepository extends JpaRepository<SubTest,String> {
    //any query

    List<SubTest> findByTestId(String testId);
}
