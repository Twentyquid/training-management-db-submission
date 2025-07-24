package com.litmus7.employeeManager.controller;

import java.util.Map;
import com.litmus7.employeeManager.dto.Response;
import com.litmus7.employeeManager.service.EmployeeManagerService;


public class EmployeeManagerController {
    
    public Response<Map<String, Integer>> writeDataToDB(String filepath) {
        if (filepath == null || filepath.isEmpty()) {
            return Response.failure("File path cannot be null or empty");
        }
        // Delegate the file processing to the service layer
       return EmployeeManagerService.loadAndSaveCsvFile(filepath);
    }

}
