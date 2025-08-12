package com.litmus7.employeeManager.ui;

import com.litmus7.employeeManager.constant.StatusCodes;

import java.util.Map;
import com.litmus7.employeeManager.dto.Response;
import com.litmus7.employeeManager.controller.EmployeeManagerController;
import com.litmus7.employeeManager.dto.Employee;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "./src/main/resources/data/employees.csv";
        EmployeeManagerController file1 = new EmployeeManagerController();

        Response<Map<String, Integer>> result = file1.writeDataToDB(filePath);
        if (result.getStatusCode() == StatusCodes.SUCCESS) {
            System.out.println("Data processed successfully: " + result.getData());
        } else {
            System.out.println("Error: " + result.getMessage());
        }

        Response<List<Employee>> employeeListResponse = EmployeeManagerController.getAllEmployees();
        if (employeeListResponse.getStatusCode() != StatusCodes.SUCCESS) {
            System.out.println("Error fetching employees: " + employeeListResponse.getMessage());
        } else {
            List<Employee> employees = employeeListResponse.getData();
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }

        Employee newEmployee = new Employee(101, "John", "Doe", "john.doe@example.com", "1234567890", "IT", 60000.0,
                new java.sql.Date(System.currentTimeMillis()));
        Response<Boolean> saveResponse = EmployeeManagerController.saveEmployee(newEmployee);
        if (saveResponse.getStatusCode() == StatusCodes.SUCCESS && saveResponse.getData()) {
            System.out.println("Employee saved successfully.");
        } else {
            System.out.println("Error saving employee: " + saveResponse.getMessage());
        }

        newEmployee.setFirstName("Jonathan");
        Response<Boolean> updateResponse = EmployeeManagerController.updateEmployee(newEmployee);
        if (updateResponse.getStatusCode() == StatusCodes.SUCCESS && updateResponse.getData()) {
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Error updating employee: " + updateResponse.getMessage());
        }

        Response<Employee> getByIdResponse = EmployeeManagerController.getEmployeeById(101);
        if (getByIdResponse.getStatusCode() == StatusCodes.SUCCESS) {
            System.out.println("Fetched employee: " + getByIdResponse.getData());
        } else {
            System.out.println("Error fetching employee by ID: " + getByIdResponse.getMessage());
        }

        Response<Boolean> deleteResponse = EmployeeManagerController.deleteEmployeeById(101);
        if (deleteResponse.getStatusCode() == StatusCodes.SUCCESS && deleteResponse.getData()) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Error deleting employee: " + deleteResponse.getMessage());
        }

        // Create a list of Employee with dummy data
        List<Employee> employeeList = List.of(
                new Employee(201, "Alice", "Smith", "alice.smith@example.com", "1112223333", "HR", 50000.0,
                        new java.sql.Date(System.currentTimeMillis())),
                new Employee(202, "Bob", "Johnson", "bob.johnson@example.com", "2223334444", "Finance", 55000.0,
                        new java.sql.Date(System.currentTimeMillis())),
                new Employee(203, "Charlie", "Williams", "charlie.williams@example.com", "3334445555", "IT", 60000.0,
                        new java.sql.Date(System.currentTimeMillis())));

        // Call addEmployeesInBatch from the controller
        Response<Boolean> batchResponse = EmployeeManagerController.addEmployeesInBatch(employeeList);
        if (batchResponse.getStatusCode() == StatusCodes.SUCCESS && batchResponse.getData()) {
            System.out.println("Employees added in batch successfully.");
        } else {
            System.out.println("Error adding employees in batch: " + batchResponse.getMessage());
        }
    }

}