package com.springboot.crud.utils;

import com.springboot.crud.entity.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CSVGenerator {

    public void writeEmployeeToCSV(List<Employee> employees, Writer writer){
        try {

            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.EXCEL);
            for (Employee employee : employees){

                printer.printRecord(employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmployeeNip(),employee.getEmployeeStatus(),employee.getEmployeePhone(),employee.getEmployeeEmail(),employee.getEmployeeDob(),employee.getEmployeeAddress());
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
