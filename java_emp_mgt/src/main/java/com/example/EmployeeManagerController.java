package com.example;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.io.FileReader;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
public class EmployeeManagerController {
    String filepath;
    private HashSet<String> duplicates = new HashSet<>();

    EmployeeManagerController(String filepath){
        this.filepath = filepath;
    }
    public static void checkConnection() {
        try(Connection conn = DatabaseConnector.getConnection()) {
            System.out.println("\u001B[32m" + "Connection Successful" + "\u001B[0m");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private List<String[]> readCSV(String filepath) {
        System.out.println(filepath);
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filepath)).withSkipLines(1).build()) {
            return reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isValidDate(String dateStr) {
    try {
        LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd
        return true;
    } catch (DateTimeParseException e) {
        return false;
    }
}
    private boolean isValidRecord(String[] row, int line) {
        String empId = row[0];
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if(duplicates.contains(empId)) {
            System.out.printf("Record %d \u001B[33m [Warning] \u001B[0m: Duplicate entry emp_id: " + empId + " skipping record %n", line);
            return false;
        } else {
            duplicates.add(empId);
        }
        String firstName = row[1];
        if (firstName == null) {
            System.out.printf("Record %d \u001B[33m [Warning] \u001B[0m: First name is missing: %n", line);
        }
        if(row[2] == null) {
            System.out.printf("Record %d [Warning] \u001B[0m: last name is missing: %n", line);
        }
        if(!row[3].matches(emailRegex)) {
            System.out.printf("Record %d \u001B[31m [Error] \u001B[0m: invalid email: %s skipping record %n", line, row[3]);
            return false;
        }
        if(!isValidDate(row[7])) {
            System.out.printf("Record %d \u001B[31m [Error] \u001B[0m: invalid date skipping record %n", line);
            return false;
        }
        return true;

    }
    public void writeDataToDB() throws Exception {
        List<String[]> data = readCSV(this.filepath);
        int line = 0;
        for(String[] row : data) {
           if(isValidRecord(row, line)) {
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
                    System.out.printf("Record %d \u001B[32m [Success] \u001B[0m: inserted to database %n", line);
                } catch (Exception e) {
                   System.out.printf("Record %d \u001B[31m [Error] \u001B[0m: error while inserting to database %s %n", line, e.getMessage());
                }
           };
           line++;
        }
    }

}
