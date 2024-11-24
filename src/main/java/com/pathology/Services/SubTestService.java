package com.pathology.Services;

import com.pathology.entities.SubTest;

import java.util.List;

public interface SubTestService {

    SubTest create (SubTest subTest);

    SubTest getById(String subTestId);

    List<SubTest> getAll ();

    void update (String subTestId,String updatedValue);

    List<SubTest> findByTestId(String testId);
}
