package com.springboot.crud.utils;

import com.springboot.crud.entity.Employee;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Employee> employeeList;

    public ExcelGenerator(List<Employee> employeeList) {
        this.employeeList = employeeList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet = workbook.createSheet("Employee");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(18);

        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setFont(font);

        createCell(row,0,"Employee Id",style);
        createCell(row,1,"Employee Name",style);
        createCell(row,2,"Employee Nip",style);
        createCell(row,3,"Employee Status",style);
        createCell(row,4,"Employee Phone",style);
        createCell(row,5,"Employee Email",style);
        createCell(row,6,"Employee TTL",style);
        createCell(row,7,"Employee Alamat",style);
    }

    private void createCell(Row row, int ColumnCount,Object value, CellStyle style){
        sheet.autoSizeColumn(ColumnCount);
        Cell cell = row.createCell(ColumnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(){
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(15);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setFont(font);

        for (Employee employee : employeeList){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row,columnCount++,employee.getEmployeeId(),style);
            createCell(row,columnCount++,employee.getEmployeeName(),style);
            createCell(row,columnCount++,employee.getEmployeeNip(),style);
            createCell(row,columnCount++,employee.getEmployeeStatus(),style);
            createCell(row,columnCount++,employee.getEmployeePhone(),style);
            createCell(row,columnCount++,employee.getEmployeeEmail(),style);
            createCell(row,columnCount++,employee.getEmployeeDob(),style);
            createCell(row,columnCount++,employee.getEmployeeAddress(),style);
        }
    }

    public void exportExcel(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
