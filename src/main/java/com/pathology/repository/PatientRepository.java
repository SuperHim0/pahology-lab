package com.pathology.repository;

import com.pathology.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
    //custom query
//    Patient updatePatientByPatientId(Patient patient);

    int deletePatientByPatientId(String patientId);

}
