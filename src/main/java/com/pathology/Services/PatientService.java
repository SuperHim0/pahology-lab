package com.pathology.Services;

import com.pathology.entities.Patient;

import java.util.List;

public interface  PatientService {

    //save patient
    Patient create(Patient patient);

    //get all patient
    List<Patient> getAll();

    //get by patient id
    Patient getByPatientId(String patientId);

    //update patient
    void updatePatient(Patient patient);

    //delete patient
    void deletePatient(String patientId);

    void getTestDetailsByPatientId(String patientId);

}
