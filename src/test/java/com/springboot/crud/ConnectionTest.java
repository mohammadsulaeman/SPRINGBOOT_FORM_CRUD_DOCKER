package com.springboot.crud;

import com.springboot.crud.utils.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class ConnectionTest {


    @Test
    void testEmployeeAll() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = """
                SELECT * FROM employee
                """;
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String id = resultSet.getString("employee_id");
            String name = resultSet.getString("employee_name");
            String email = resultSet.getString("employee_email");

            System.out.println(
                    String.join(", ", id, name, email)
            );
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    void testPreparedStatemend() throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String name = "Mohammad Sulaeman";
        String searchName = "SELECT * FROM employee_db.employee WHERE employee_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchName);
        preparedStatement.setString(1,name);

        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println(preparedStatement.toString());
        /**
         * Set Up Data
         */
        String employeeName = "";
        String employeeNip = "";
        String employeeStatus = "";
        String employeePhone = "";
        String employeeEmail = "";
        String employeeDob = "";
        String employeeAddress = "";

        while (resultSet.next()){
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
        }
        preparedStatement.close();
        connection.close();
    }
}
