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

}
