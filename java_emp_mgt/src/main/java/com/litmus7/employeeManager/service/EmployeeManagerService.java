package com.litmus7.employeeManager.service;

// import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.litmus7.employeeManager.dao.EmployeeDao;
import com.litmus7.employeeManager.dto.Employee;
import com.litmus7.employeeManager.exception.EmployeeDaoException;
import com.litmus7.employeeManager.exception.EmployeeServiceException;
// import com.litmus7.employeeManager.dto.Response;
import com.litmus7.employeeManager.helper.EmployeeHelper;
import com.litmus7.employeeManager.util.ReadCSVFile;
// import com.opencsv.exceptions.CsvException;

public class EmployeeManagerService {

    public static Map<String, Integer> loadAndSaveCsvFile(String filePath) throws EmployeeServiceException {
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new EmployeeServiceException("Invalid file type. Only CSV files are allowed.", null);
        }
        Set<String> processedEmpIDs = new HashSet<>(); // to identify duplicate records
        Map<String, Integer> recordStats = new HashMap<>(); // to track uploaded and skipped records
        recordStats.put("skipped_files", 0);
        recordStats.put("uploaded_files", 0);
        List<String[]> data = new ArrayList<>();
        try {
            data = ReadCSVFile.readCSV(filePath);
        } catch (Exception e) {
            throw new EmployeeServiceException("Error reading CSV file: " + e.getMessage(), e);
        }
        // } catch (IOException e) {
        // return Response.failure("Error reading CSV file: " + e.getMessage());
        // } catch (CsvException e) {
        // return Response.failure("Error parsing CSV file: " + e.getMessage());
        // }
        for (String[] row : data) {
            if (EmployeeHelper.isValidEmployeeRecord(row, processedEmpIDs)) {
                try {
                    if (EmployeeDao.saveEmployee(row)) {
                        recordStats.put("uploaded_files", recordStats.get("uploaded_files") + 1);
                    } else {
                        recordStats.put("skipped_files", recordStats.get("skipped_files") + 1);
                    }
                } catch (EmployeeDaoException e) {
                    throw new EmployeeServiceException("Error saving employee: " + e.getMessage(), e);
                }
            } else {
                recordStats.put("skipped_files", recordStats.get("skipped_files") + 1);
            }
        }
        return recordStats;
    }

    public static boolean saveEmployee(Employee employee) throws EmployeeServiceException {
        if (employee == null) {
            throw new EmployeeServiceException("Invalid employee data", null);
        }
        try {
            return EmployeeDao.saveEmployee(employee);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Error saving employee: " + e.getMessage(), e);
        }
    }

    public static List<Employee> getAllEmployees() throws EmployeeServiceException {
        try {
            return EmployeeDao.getAllEmployees();
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Error fetching employees: " + e.getMessage(), e);
        }
    }

    public static Employee getEmployeeById(int empId) throws EmployeeServiceException {
        try {
            return EmployeeDao.getEmployeeById(empId);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Error fetching employee by ID: " + e.getMessage(), e);
        }
    }

    public static boolean deleteEmployeeById(int empId) throws EmployeeServiceException {
        try {
            return EmployeeDao.deleteEmployeeById(empId);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Error deleting employee by ID: " + e.getMessage(), e);
        }
    }

    public static boolean updateEmployee(Employee employee) throws EmployeeServiceException {
        try {
            return EmployeeDao.updateEmployee(employee);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Error updating employee: " + e.getMessage(), e);
        }
    }
}
