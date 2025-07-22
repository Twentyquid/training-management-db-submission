package com.litmus7.employeeManager;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import com.litmus7.employeeManager.util.ValidationUtils;
public class EmployeeManagerController {

    private List<String[]> readCSV(FileReader fileToRead) throws CsvException, IOException {
        List<String []> output = new ArrayList<>();
        CSVReader reader = new CSVReaderBuilder(fileToRead).withSkipLines(1).build();
        output = reader.readAll();
        return output;
    }

    private boolean isValidRecord(String[] row, Set<String> processedEmpIDs) {
        boolean valid = true;
        String empId = row[0];
        if(processedEmpIDs.contains(empId)) {
            valid = false;
        } else {
           processedEmpIDs.add(empId);
        }
        if(!ValidationUtils.isValidEmail(row[3])) {
           valid = false;
        }
        if(!ValidationUtils.isValidDate(row[7])) {
            valid = false;
        }
        if(!ValidationUtils.isValidPhoneNumber(row[4])){
            valid = false;
        }
        return valid;

    }
    public Map<String, Integer> writeDataToDB(String filepath) throws Exception {
        // thorw error if not a csv file
        if(!filepath.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Only csv files supported");
        }
        Set<String> processedEmpIDs = new HashSet<>(); // to identify duplicate records
        Map<String, Integer> recordStats = new HashMap<>(); // to track uploaded and skipped records
        recordStats.put("skipped_files", 0);
        recordStats.put("uploaded_files", 0);
        List<String[]> data = new ArrayList<>();
        try( FileReader fileToRead = new FileReader(filepath)){
            data = readCSV(fileToRead);
        }
        for(String[] row : data) {
           if(isValidRecord(row, processedEmpIDs)) {
                String inserQuery = "INSERT INTO employees (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (Connection conn = DatabaseConnector.getConnection()) {
                    PreparedStatement stmt = conn.prepareStatement(inserQuery);
                    stmt.setInt(1, Integer.parseInt(row[0]));
                    stmt.setString(2, row[1]);
                    stmt.setString(3, row[2]);
                    stmt.setString(4, row[3]);
                    stmt.setString(5, row[4]);
                    stmt.setString(6, row[5]);
                    stmt.setInt(7, Integer.parseInt(row[6]));
                    stmt.setDate(8, java.sql.Date.valueOf(row[7]));
                    stmt.executeUpdate();
                    recordStats.put("uploaded_files", recordStats.get("uploaded_files") + 1);
                } 
           } else {
            recordStats.put("skipped_files", recordStats.get("skipped_files") + 1);
           } ;
        }
        return recordStats;
    }

}
