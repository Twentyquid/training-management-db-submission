package com.litmus7.employeeManager.constant;

public class Constants {
    private Constants() {}
    public static final String INSERT_EMPLOYEE = "INSERT INTO employees (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE emp_id = ?";
    public static final String GET_ALL_EMPLOYEES = "SELECT * FROM employees";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PHONE_REGEX = "^(\\+91-)?[6-9]\\d{9}$";
    public static final String DATE_REGEX = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
}
