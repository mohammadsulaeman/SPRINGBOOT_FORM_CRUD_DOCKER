package com.springboot.crud.service.impl;

import com.springboot.crud.entity.Employee;
import com.springboot.crud.repository.EmployeeRepository;
import com.springboot.crud.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeImplService implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> fetchEmployeeALl() {
        return (List<Employee>) employeeRepository.findAll();
    }



    @Override
    public Employee fetchEmployeeById(Integer employeeId) throws NoSuchElementException{
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee;
        if (optionalEmployee.isPresent()){
            employee = optionalEmployee.get();
        }else {
            throw new NoSuchElementException("Employee not found");
        }
        return employee;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }



    @Override
    public void deleteEmployeeById(Integer employeId) throws NoSuchElementException {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeId);
        if (employeeOptional.isPresent()){
            employeeRepository.deleteById(employeId);
        }else {
            throw new NoSuchElementException("Employee not found");
        }
    }


}
