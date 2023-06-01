package com.springboot.crud.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.crud.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    private List<Employee> employeeList;

    public PDFGenerator(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.CYAN);
        cell.setPadding(10);

        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.white);

        cell.setPhrase(new Phrase("Employee Id",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Nip",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Status",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Phone",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Email",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee TTL",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Alamat",font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table){
        for (Employee employee : employeeList){
            table.addCell(String.valueOf(employee.getEmployeeId()));
            table.addCell(String.valueOf(employee.getEmployeeName()));
            table.addCell(String.valueOf(employee.getEmployeeNip()));
            table.addCell(String.valueOf(employee.getEmployeeStatus()));
            table.addCell(String.valueOf(employee.getEmployeePhone()));
            table.addCell(String.valueOf(employee.getEmployeeEmail()));
            table.addCell(String.valueOf(employee.getEmployeeDob()));
            table.addCell(String.valueOf(employee.getEmployeeAddress()));
        }
    }

    public void exportPdf(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(15);
        font.setColor(Color.CYAN);

        Paragraph p = new Paragraph("List of Employee",font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(108f);
        table.setWidths(new float[]{10.0f,10.0f,10.0f,10.0f,10.0f,10.0f,10.0f,10.0f});
        table.setSpacingBefore(5);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.addAuthor("Mohammad Sulaeman");
        document.addCreator("Mohammad Sulaeman");
        document.close();
    }
}
