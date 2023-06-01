package com.springboot.crud.controller;

import com.lowagie.text.DocumentException;
import com.springboot.crud.entity.Employee;
import com.springboot.crud.service.EmployeeService;
import com.springboot.crud.utils.CSVGenerator;
import com.springboot.crud.utils.ConnectionUtil;
import com.springboot.crud.utils.ExcelGenerator;
import com.springboot.crud.utils.PDFGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CSVGenerator csvGenerator;


    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("employees",employeeService.fetchEmployeeALl());
        return "index";
    }

    @GetMapping("/search")
    public String searchEmployeeByName(@RequestParam(value = "employeeName") String name, Model model) throws SQLException {

        System.out.println("Name = "+name);
        /**
         * connection sql database mysql
         */
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String searchName = "SELECT * FROM employee_db.employee WHERE employee_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchName);
        preparedStatement.setString(1,name);

        System.out.println("SQL SEARCH = "+preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();

        /**
         * Set Up Data
         */
        Integer employeeId = 0;
        String employeeName = "";
        String employeeNip = "";
        String employeeStatus = "";
        String employeePhone = "";
        String employeeEmail = "";
        String employeeDob = "";
        String employeeAddress = "";

        if (resultSet.next()){
            employeeId = resultSet.getInt("employee_id");
            employeeName = resultSet.getString("employee_name");
            employeeNip = resultSet.getString("employee_nip");
            employeeStatus = resultSet.getString("employee_status");
            employeePhone = resultSet.getString("employee_phone");
            employeeEmail = resultSet.getString("employee_email");
            employeeDob = resultSet.getString("employee_dob");
            employeeAddress = resultSet.getString("employee_address");
            System.out.println(
                    String.join(", ",employeeName,employeeNip,employeeStatus,employeePhone,employeeEmail,employeeDob,employeeAddress)
            );
        }else {
            return "not_found";
        }

        /**
         * SET UP DATA To ENTITY EMPLOYEE
         */
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeName);
        employee.setEmployeeNip(employeeNip);
        employee.setEmployeeStatus(employeeStatus);
        employee.setEmployeePhone(employeePhone);
        employee.setEmployeeEmail(employeeEmail);
        employee.setEmployeeDob(employeeDob);
        employee.setEmployeeAddress(employeeAddress);
        System.out.println("Employee = "+employee);
        model.addAttribute("employess", employee);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return "search";

    }
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/showFormUpdateEmployee/{id}")
    public String showFormUpdateEmployee(@PathVariable("id") Integer employeId, Model model){
        Employee employee = employeeService.fetchEmployeeById(employeId);
        model.addAttribute("employee",employee);
        return "update_employee";
    }

   @GetMapping("/deleteEmployee/{id}")
   public String deleteEmployee(@PathVariable("id") Integer employeeId){
        employeeService.deleteEmployeeById(employeeId);
        return "redirect:/";
   }


   @GetMapping("/export/pdf")
   public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException{
        response.setContentType("application/pdf");
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       String currentDateTime = dateFormat.format(new Date());

       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=employee_" + currentDateTime + ".pdf";
       response.setHeader(headerKey, headerValue);

       List<Employee> employeeList = employeeService.fetchEmployeeALl();

       PDFGenerator generator = new PDFGenerator(employeeList);
       generator.exportPdf(response);
   }

   @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
       response.setContentType("application/octet-stream");
       DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       String currentDateTime = dateFormatter.format(new Date());

       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=employee_" + currentDateTime + ".xlsx";
       response.setHeader(headerKey, headerValue);

       List<Employee> employeeList = employeeService.fetchEmployeeALl();

       ExcelGenerator generator = new ExcelGenerator(employeeList);
       generator.exportExcel(response);
   }

   @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
       DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       String currentDateTime = dateFormatter.format(new Date());
       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=employee_" +  currentDateTime + ".csv";
       response.setHeader(headerKey, headerValue);
       csvGenerator.writeEmployeeToCSV(employeeService.fetchEmployeeALl(),response.getWriter());
   }

   @GetMapping("/export/pdf/{id}")
   public void exportPDFById(@PathVariable("id") Integer employeeId, HttpServletResponse response ) throws IOException {
       response.setContentType("application/pdf");
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       String currentDateTime = dateFormat.format(new Date());
       Employee employee = employeeService.fetchEmployeeById(employeeId);
       String nama = employee.getEmployeeName();
       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=employee_" + nama + "_" + currentDateTime + ".pdf";
       response.setHeader(headerKey, headerValue);


       List<Employee> employeeList = new ArrayList<>();
       employeeList.add(employee);

       PDFGenerator generator = new PDFGenerator(employeeList);
       generator.exportPdf(response);
   }

    @GetMapping("/export/excel/{id}")
    public void exportExcelById(@PathVariable("id") Integer employeeId, HttpServletResponse response) throws IOException{
       response.setContentType("application/octet-stream");
       DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       String currentDateTime = dateFormatter.format(new Date());

       Employee employee = employeeService.fetchEmployeeById(employeeId);
       List<Employee> employeeList = new ArrayList<>();
       employeeList.add(employee);
       String nama = employee.getEmployeeName();
       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=employee_" + nama + "_"+ currentDateTime + ".xlsx";
       response.setHeader(headerKey, headerValue);
       ExcelGenerator generator = new ExcelGenerator(employeeList);
       generator.exportExcel(response);
   }

   @GetMapping("/export/csv/{id}")
    public void exportCsvById(@PathVariable("id") Integer employeeId, HttpServletResponse response) throws IOException {
       response.setContentType("text/csv");
       DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       String currentDateTime = dateFormatter.format(new Date());

       Employee employee = employeeService.fetchEmployeeById(employeeId);
       String nama = employee.getEmployeeName();
       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=employee_" + nama + "_" + currentDateTime + ".csv";
       response.addHeader(headerKey, headerValue);
       List<Employee> employeeList = new ArrayList<>();
       employeeList.add(employee);
       csvGenerator.writeEmployeeToCSV(employeeList,response.getWriter());
   }
}
