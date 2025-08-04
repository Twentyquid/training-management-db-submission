package com.litmus7.employeeManager.service;

// import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.litmus7.employeeManager.dao.EmployeeDao;
// import com.litmus7.employeeManager.dto.Response;
import com.litmus7.employeeManager.helper.EmployeeHelper;
import com.litmus7.employeeManager.util.ReadCSVFile;
// import com.opencsv.exceptions.CsvException;

public class EmployeeManagerService {

    public static Map<String, Integer> loadAndSaveCsvFile(String filePath) throws Exception {
        if(!filePath.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid file type. Only CSV files are allowed.");
        }
        Set<String> processedEmpIDs = new HashSet<>(); // to identify duplicate records
        Map<String, Integer> recordStats = new HashMap<>(); // to track uploaded and skipped records
        recordStats.put("skipped_files", 0);
        recordStats.put("uploaded_files", 0);
        List<String[]> data = new ArrayList<>();
       
            data = ReadCSVFile.readCSV(filePath);
        // } catch (IOException e) {
        //     return Response.failure("Error reading CSV file: " + e.getMessage());
        // } catch (CsvException e) {
        //     return Response.failure("Error parsing CSV file: " + e.getMessage());
        // }
        for(String[] row : data) {
           if(EmployeeHelper.isValidEmployeeRecord(row, processedEmpIDs)) {
               if(EmployeeDao.saveEmployee(row)) {
                   recordStats.put("uploaded_files", recordStats.get("uploaded_files") + 1);
               } else {
                   recordStats.put("skipped_files", recordStats.get("skipped_files") + 1);
               }
           } else {
            recordStats.put("skipped_files", recordStats.get("skipped_files") + 1);
           }
        }
        return recordStats;
    }
}
