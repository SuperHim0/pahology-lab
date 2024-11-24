package com.pathology.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subtest")
public class SubTest {
    @Id
    @Column(name = "subTestId")
    private String subTestId;
    @Column(name = "testId")
    private String testId;
    @Column(name = "subTestName")
    private String subTestName;
    private String units;
    private String value;
    private String rangeValue; //never use name range it conflict with the query
}
