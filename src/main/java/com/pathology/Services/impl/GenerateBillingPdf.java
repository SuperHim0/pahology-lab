package com.pathology.Services.impl;

import com.pathology.Services.PatientService;
import com.pathology.Services.SubTestService;
import com.pathology.Services.TestService;
import com.pathology.entities.Patient;
import com.pathology.entities.SubTest;
import com.pathology.entities.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
//import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;


@Service
public class GenerateBillingPdf {

    @Autowired
    private SubTestService subTestService;

    @Autowired
    private TestService testService;

    @Autowired
    private PatientService patientService;

  @Autowired
  private Logger logger = LoggerFactory.getLogger(GenerateBillingPdf.class);




    private Cell createCell(String content, boolean isHeader) {
        Cell cell = new Cell()
                .add(new Paragraph(content).setFontSize(isHeader ? 10 : 9));
        cell.setBorder(Border.NO_BORDER); // Remove inner borders
        return cell;
    }
    private Cell createCellWithBorder(String content, boolean isHeader) {
        Cell cell = new Cell()
                .add(new Paragraph(content).setFontSize(isHeader ? 10 : 9));
//        cell.setBorder(Border.NO_BORDER); // Remove inner borders
        cell.setBackgroundColor(new DeviceRgb(Color.gray));
        return cell;
    }

    private void addPatientRow(Table table, String label, String value) {
        table.addCell(createCell(label, true));
        table.addCell(createCell(value, false));
    }

    private void addTestRow(Table table, String testName, String result,String units, String reference, boolean isHeader) {
        table.addCell(createCell(testName, isHeader));
        String[] str = reference.split("-");
        System.out.println(Arrays.toString(str));
        System.out.println(result);
        float rangeFirst = Float.parseFloat(str[0]);
        float rangeSecond = Float.parseFloat(str[1]);
        float value = Float.parseFloat(result);
        if(value<rangeFirst||value>rangeSecond){
            table.addCell(createCell(result+"*", isHeader).setBold());
        }
        else {
            table.addCell(createCell(result,isHeader));
        }
        table.addCell(createCell(units,isHeader));
        table.addCell(createCell(reference, isHeader));
    }
    private void addTestHead(Table table, String testName, String result,String units, String reference, boolean isHeader) {
        table.addCell(createCell(testName, isHeader));
        table.addCell(createCell(result,isHeader));
        table.addCell(createCell(units,isHeader));
        table.addCell(createCell(reference, isHeader));
    }



    public void generateCbcReport(HttpServletResponse response ,String patientId) {
        try {
//             Set response headers
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=cbc_report.pdf");

            //
//            String path = "src\\main\\resources\\static\\cbc.pdf";
//            File directory = new File(path);
//            if (!directory.exists()) {
//                System.out.println("Creating the path: " + directory.getAbsolutePath());
//                if (directory.createNewFile()) {
//                    System.out.println("Directory created successfully.");
//                } else {
//                    System.out.println("Failed to create the directory.");
//                }
//            } else {
//                System.out.println("Directory already exists: " + directory.getAbsolutePath());
//            }

            // Initialize PDF document
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

//            PdfWriter.getInstance(document);
            // Header
            Paragraph header = new Paragraph("DEMO LAB\nDIAGNOSTIC CENTER")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(16)
                    .setBold();
            document.add(header);

            document.add(new Paragraph("\n")); // Spacer
            Table patientDetailsTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                    .setWidth(UnitValue.createPercentValue(100));

            // Left-hand side details
            Table lhsTable = new Table(UnitValue.createPercentArray(new float[]{2, 3}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setBorder(Border.NO_BORDER);
            //patient details
            Patient patient = patientService.getByPatientId(patientId);

            addPatientRow(lhsTable, "Patient Name:", patient.getSrName()+" "+patient.getName());
            addPatientRow(lhsTable, "Age & Sex:", patient.getAge()+" / "+patient.getGender());
            addPatientRow(lhsTable,"Father Name:", patient.getFatherName());
            addPatientRow(lhsTable, "Contact No:", patient.getMobile());
            addPatientRow(lhsTable, "Address:", patient.getAddress());

            // Right-hand side details
            Table rhsTable = new Table(UnitValue.createPercentArray(new float[]{2, 3}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setBorder(Border.NO_BORDER);

            addPatientRow(rhsTable, "Reporting Date:", ""+patient.getReportingDate());
            addPatientRow(rhsTable, "Collection Date:", ""+patient.getCollectionDate());
            addPatientRow(rhsTable, "Referred By:", patient.getRefBy());
            addPatientRow(rhsTable, "Ref. No.:", "");

//            addPatientRow(rhsTable, "Reporting Date:", "01-01-2023 2:10 PM");
//            addPatientRow(rhsTable, "Collection Date:", "01-01-2023 1:03 PM");
//            addPatientRow(rhsTable, "Referred By:", "Dr Abhishek (MS MCH)");
//            addPatientRow(rhsTable, "Ref. No.:", "12345");


            // Add LHS and RHS tables into the parent table
            patientDetailsTable.addCell(new Cell().add(lhsTable));
            patientDetailsTable.addCell(new Cell().add(rhsTable));

            // Add patient details table to the document
            document.add(patientDetailsTable);

            document.add(new Paragraph("\n")); // Spacer

            // Hematology Section
            document.add(new Paragraph("HEMATOLOGY\n").setBold().setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));

            Table cbcTableHead = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setBorder(new SolidBorder(Border.SOLID));

            addTestHead(cbcTableHead, "Test Name", "Result", "units", "Reference Range", true);

            Table cbcTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setBorder(Border.NO_BORDER);
//                cbcTableHead.addCell("Complete Blood Count (CBC)").setBold().setFontSize(10);


            document.add(cbcTableHead);
//            document.add(new Paragraph("COMPLETE BLOOD COUNT"));

            //getting data form the testing DB
            List<Test> test = testService.findTestByPatient(patientId);
            logger.info("generating the testing");

            for (Test testDetails : test){
                String testId = testDetails.getTestId();
                List<SubTest> subTestList = subTestService.findByTestId(testId);

                document.add(new Paragraph(testDetails.getTestName()));
                for (SubTest subTest: subTestList){
                    addTestRow(cbcTable, subTest.getSubTestName(), subTest.getValue(), subTest.getUnits(), subTest.getRangeValue(), false);
                }
                document.add(cbcTable);
            }

//            Test testDetails = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("the given id is not available on server"));
           /** String testId1 = test.getTestId();
            List<SubTest> findByAllDetails = subTestService.findByTestId(testId1);
            logger.info("details of subtest:{}",findByAllDetails);
            testDetails.setSubTests(findByAllDetails);

            document.add(new Paragraph(testDetails.getTestName()));
            for (SubTest subTest : findByAllDetails){
                addTestRow(cbcTable, subTest.getSubTestName(), subTest.getValue(), subTest.getUnits(), subTest.getRangeValue(), false);
                System.out.println(subTest);
            }
            */





//                addTestRow(cbcTable, "Test Name", "Result", "Reference Range", true);
        /**    addTestRow(cbcTable, "Hemoglobin", "14.5", "g/dL", "14-18", false);
            addTestRow(cbcTable, "Packed Cell Volume (PCV)", "36.5", "%", "36-46", false);
            addTestRow(cbcTable, "RBC Count", "3.8", "million/µL", "3.8-4.8", false);
            addTestRow(cbcTable, "MCV", "96.1", "fL", "83.0-101.0", false);
            addTestRow(cbcTable, "MCH", "38.2", "pg", "27-32", false);
            addTestRow(cbcTable, "MCHC", "39.7", "g/dL", "31.5-34.5", false);
            addTestRow(cbcTable, "RDW-SD", "37.5", "fl", "37-54", false);
         */

//                document.add(new Paragraph("COMPLETE BLOOD COUNT (CBC)").setBold());
//            document.add(cbcTable);


            document.add(new Paragraph("\n")); // Spacer

            // Footer
            document.add(new Paragraph("Lab Incharge: Naman Modi\nConsultant Pathologist: Dr. M.K. Singh (Path.)")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("Note: Pathological Test have technical limitations. For any disparity, repeated examination is required. No legal liability is accepted.")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.LEFT));

            // Close document

            document.close();

            // Write PDF to response
//            FileWriter fileWriter = new FileWriter(directory);
//            fileWriter.write(baos.toString());
//            fileWriter.flush();
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
