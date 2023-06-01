package com.springboot.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    private String employeeName;

    private String employeeStatus;

    private String employeeNip;

    private String employeePhone;

    private String employeeEmail;

    private String employeeAddress;

    private String employeeDob;
}
