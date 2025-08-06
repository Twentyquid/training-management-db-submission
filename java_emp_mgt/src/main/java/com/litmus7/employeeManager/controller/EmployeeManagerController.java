package com.litmus7.employeeManager.controller;

import java.util.Map;
import com.litmus7.employeeManager.dto.Response;
import com.litmus7.employeeManager.exception.EmployeeServiceException;
import com.litmus7.employeeManager.service.EmployeeManagerService;
import com.litmus7.employeeManager.dto.Employee;
import java.util.List;

public class EmployeeManagerController {

    public Response<Map<String, Integer>> writeDataToDB(String filepath) {
        if (filepath == null || filepath.isEmpty()) {
            return Response.failure("File path cannot be null or empty");
        }
        try {
            Map<String, Integer> recordStats = EmployeeManagerService.loadAndSaveCsvFile(filepath);
            return Response.success(recordStats);
        } catch (EmployeeServiceException e) {
            return Response.failure("Error processing CSV file: " + e.getMessage());
        } catch (Exception e) {
            return Response.failure("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static Response<Boolean> saveEmployee(Employee employee) {
        if (employee == null) {
            return Response.failure("Invalid employee data");
        }
        try {
            boolean isSaved = EmployeeManagerService.saveEmployee(employee);
            return isSaved ? Response.success(true) : Response.failure("Failed to save employee");
        } catch (EmployeeServiceException e) {
            return Response.failure("Error saving employee: " + e.getMessage());
        } catch (Exception e) {
            return Response.failure("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static Response<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = EmployeeManagerService.getAllEmployees();
            return Response.success(employees);
        } catch (EmployeeServiceException e) {
            return Response.failure("Error fetching employees: " + e.getMessage());
        } catch (Exception e) {
            return Response.failure("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static Response<Employee> getEmployeeById(int empId) {
        try {
            Employee employee = EmployeeManagerService.getEmployeeById(empId);
            if (employee != null) {
                return Response.success(employee);
            } else {
                return Response.failure("Employee not found with ID: " + empId);
            }
        } catch (EmployeeServiceException e) {
            return Response.failure("Error fetching employee by ID: " + e.getMessage());
        } catch (Exception e) {
            return Response.failure("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static Response<Boolean> deleteEmployeeById(int empId) {
        try {
            EmployeeManagerService.deleteEmployeeById(empId);
            return Response.success(true);
        } catch (EmployeeServiceException e) {
            return Response.failure("Error deleting employee: " + e.getMessage());
        } catch (Exception e) {
            return Response.failure("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static Response<Boolean> updateEmployee(Employee employee) {
        if (employee == null) {
            return Response.failure("Invalid employee data");
        }
        try {
            boolean isUpdated = EmployeeManagerService.updateEmployee(employee);
            return isUpdated ? Response.success(true) : Response.failure("Failed to update employee");
        } catch (EmployeeServiceException e) {
            return Response.failure("Error updating employee: " + e.getMessage());
        } catch (Exception e) {
            return Response.failure("An unexpected error occurred: " + e.getMessage());
        }
    }

}
