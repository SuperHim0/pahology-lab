package com.pathology.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    private String patientId;
    private String srName;
    private String name;
    private String fatherName;
    private String age;
    private String gender;
    private String mobile;
    private String address;
    private String refBy;
    private String date;
    private String reportingDate;
    private String collectionDate;
    private int uHID;

    @Transient
    private List<Test> test;

}
