package com.litmus7.employeeManager.dao;

import com.litmus7.employeeManager.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.litmus7.employeeManager.constant.Constants;
import com.litmus7.employeeManager.dto.Employee;
import com.litmus7.employeeManager.exception.EmployeeDaoException;

import java.util.List;
import java.util.ArrayList;

public class EmployeeDao {
    public static boolean saveEmployee(String[] row) throws EmployeeDaoException {
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
            throw new EmployeeDaoException("Error saving employee: " + e.getMessage(), e);
        }
    }

    public static boolean saveEmployee(Employee employee) throws EmployeeDaoException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String insertQuery = Constants.INSERT_EMPLOYEE;
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1, employee.getId());
                pstmt.setString(2, employee.getFirstName());
                pstmt.setString(3, employee.getLastName());
                pstmt.setString(4, employee.getEmail());
                pstmt.setString(5, employee.getPhoneNumber());
                pstmt.setString(6, employee.getDepartment());
                pstmt.setDouble(7, employee.getSalary());
                pstmt.setDate(8, new Date(employee.getJoinDate().getTime()));
                return pstmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            throw new EmployeeDaoException("Error saving employee: " + e.getMessage(), e);
        }
    }

    public static List<Employee> getAllEmployees() throws EmployeeDaoException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String selectQuery = Constants.GET_ALL_EMPLOYEES;
            try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                ResultSet rs = pstmt.executeQuery();
                List<Employee> employees = new ArrayList<>();
                while (rs.next()) {
                    Employee employee = new Employee(
                            rs.getInt("emp_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("department"),
                            rs.getDouble("salary"),
                            rs.getDate("join_date"));
                    employees.add(employee);
                }
                return employees;
            }
        } catch (Exception e) {
            throw new EmployeeDaoException("Error fetching employees: " + e.getMessage(), e);
        }
    }

    public static Employee getEmployeeById(int empId) throws EmployeeDaoException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String selectQuery = Constants.SELECT_EMPLOYEE_BY_ID;
            try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                pstmt.setInt(1, empId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("emp_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("department"),
                            rs.getDouble("salary"),
                            rs.getDate("join_date"));
                }
            }
        } catch (Exception e) {
            throw new EmployeeDaoException("Error fetching employee by ID: " + e.getMessage(), e);
        }
        return null;
    }

    public static boolean deleteEmployeeById(int empId) throws EmployeeDaoException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String deleteQuery = Constants.DELETE_EMPLOYEE_BY_ID;
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, empId);
                return pstmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            throw new EmployeeDaoException("Error deleting employee: " + e.getMessage(), e);
        }
    }

    public static boolean updateEmployee(Employee employee) throws EmployeeDaoException {
        if (employee == null) {
            throw new EmployeeDaoException("Employee object is null", null);
        }
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone = ?, department = ?, salary = ?, join_date = ? WHERE emp_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhoneNumber());
            pstmt.setString(5, employee.getDepartment());
            pstmt.setDouble(6, employee.getSalary());
            pstmt.setDate(7, new Date(employee.getJoinDate().getTime()));
            pstmt.setInt(8, employee.getId());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new EmployeeDaoException("Error updating employee: " + e.getMessage(), e);
        }
    }

    public static boolean addEmployeesInBatch(List<Employee> employeeList) throws EmployeeDaoException {
        if (employeeList == null || employeeList.isEmpty()) {
            throw new EmployeeDaoException("Employee list is null or empty", null);
        }
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.INSERT_EMPLOYEE)) {
                conn.setAutoCommit(false);
                for (Employee employee : employeeList) {
                    pstmt.setInt(1, employee.getId());
                    pstmt.setString(2, employee.getFirstName());
                    pstmt.setString(3, employee.getLastName());
                    pstmt.setString(4, employee.getEmail());
                    pstmt.setString(5, employee.getPhoneNumber());
                    pstmt.setString(6, employee.getDepartment());
                    pstmt.setDouble(7, employee.getSalary());
                    pstmt.setDate(8, new Date(employee.getJoinDate().getTime()));
                    pstmt.addBatch();
                }
                int[] results = pstmt.executeBatch();
                conn.commit();
                return results.length == employeeList.size();
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new EmployeeDaoException("Error rolling back transaction: " + ex.getMessage(), ex);
                }
            }
            throw new EmployeeDaoException("Error adding employees in batch: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    throw new EmployeeDaoException("Error closing connection: " + ex.getMessage(), ex);
                }
            }
        }
    }

    public static boolean transferEmployeesToDepartment(List<Integer> employeeIds, String newDepartment)
            throws EmployeeDaoException {
        if (employeeIds == null || employeeIds.isEmpty() || newDepartment == null) {
            throw new EmployeeDaoException("Employee IDs or new department is null/empty", null);
        }
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            String sql = Constants.UPDATE_DEPARTMENT;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false);
                for (Integer empId : employeeIds) {
                    pstmt.setString(1, newDepartment);
                    pstmt.setInt(2, empId);
                    pstmt.addBatch();
                }
                int[] results = pstmt.executeBatch();
                for (int res : results) {
                    if (res == PreparedStatement.EXECUTE_FAILED) {
                        conn.rollback();
                        return false;
                    }
                }
                conn.commit();
                return true;
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new EmployeeDaoException("Error rolling back transaction: " + ex.getMessage(), ex);
                }
            }
            throw new EmployeeDaoException("Error transferring employees to department: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    throw new EmployeeDaoException("Error closing connection: " + ex.getMessage(), ex);
                }
            }
        }
    }
}
