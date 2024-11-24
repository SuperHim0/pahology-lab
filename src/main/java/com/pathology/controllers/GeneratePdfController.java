package com.pathology.controllers;

import com.pathology.Services.TestService;
import com.pathology.Services.impl.GenerateBillingPdf;
import com.pathology.entities.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class GeneratePdfController {

    @Autowired
    private GenerateBillingPdf generateBillingPdf;

    @Autowired
    private TestService testService;

    @GetMapping("/generate-cbc-pdf/{patientId}")
    public void generateCbcReport(HttpServletResponse response,@PathVariable String patientId) {
        generateBillingPdf.generateCbcReport(response,patientId);
    }

}
