package com.litmus7.employeeManager.dao;
import com.litmus7.employeeManager.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.litmus7.employeeManager.constant.Constants;
import com.litmus7.employeeManager.dto.Response;
import com.litmus7.employeeManager.dto.Employee;
import java.util.List;
import java.util.ArrayList;

public class EmployeeDao {
    public static boolean saveEmployee(String[] row) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String insertQuery = Constants.INSERT_EMPLOYEE;
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, row[0]);
                pstmt.setString(2, row[1]);
                pstmt.setString(3, row[2]);
                pstmt.setString(4, row[3]);
                pstmt.setString(5, row[4]);
                pstmt.setString(6, row[5]);
                pstmt.setDouble(7, Double.parseDouble(row[6]));
                pstmt.setDate(8, Date.valueOf(row[7]));
                return pstmt.executeUpdate() > 0;
            }
            
        } catch (Exception e) {
            return false;
        }
    }
    public static Response<List<Employee>> getAllEmployees() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String selectQuery = Constants.GET_ALL_EMPLOYEES;
            try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                ResultSet rs = pstmt.executeQuery();
                List<Employee> employees = new ArrayList<>();
                while (rs.next()) {
                    Employee emp = new Employee(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("department"),
                            rs.getDouble("salary"),
                            rs.getDate("join_date")
                    );
                    employees.add(emp);
                }
                return Response.success(employees);
            }
        } catch (Exception e) {
            return Response.failure("Error fetching employees: " + e.getMessage());
        }
    }
}
