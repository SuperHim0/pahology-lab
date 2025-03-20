package com.pathology.Services.impl;

import com.pathology.Services.SubTestService;
import com.pathology.entities.SubTest;
import com.pathology.exception.ResourceNotFoundException;
import com.pathology.repository.SubTestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubTestServiceImpl implements SubTestService {

    private static final Logger log = LoggerFactory.getLogger(SubTestServiceImpl.class);
    @Autowired
    private SubTestRepository subTestRepository;

    @Override
    public SubTest create(SubTest subTest) {
        String randomId = UUID.randomUUID().toString();
        subTest.setSubTestId(randomId);
        return subTestRepository.save(subTest);
    }

    @Override
    public SubTest getById(String subTestId) {
        return subTestRepository.findById(subTestId).orElseThrow(
                () ->
                    new ResourceNotFoundException("data not found with the id in the dataBase")
                );
    }

    @Override
    public List<SubTest> getAll() {
        return subTestRepository.findAll();
    }

    @Override
    public void update(String subTestId,String updatedValue) {
        SubTest subTestList = subTestRepository.findById(subTestId).orElseThrow(()-> new ResourceNotFoundException("subTest Not Found by Given ID!"));
        subTestList.setValue(updatedValue);
        subTestRepository.save(subTestList);
        log.info(subTestList.getValue());
    }

    @Override
    public List<SubTest> findByTestId(String testId) {
        return subTestRepository.findByTestId(testId);
    }

    @Override
    public void delete(String subTestId) {
        subTestRepository.deleteById(subTestId);
    }
}
