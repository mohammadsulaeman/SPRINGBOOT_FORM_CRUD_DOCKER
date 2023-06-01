package com.springboot.crud.service;

import com.springboot.crud.entity.Employee;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeService {

    /**
     * method read data from database
     */
    List<Employee> fetchEmployeeALl();

    /**
     * method read data by employeeId from database
     */
    Employee fetchEmployeeById(Integer employeeId);

    /**
     * method save data to database
     */
    Employee saveEmployee(Employee employee);




    /**
     * method delete data to database
     */
    void deleteEmployeeById(Integer employeId);
}
