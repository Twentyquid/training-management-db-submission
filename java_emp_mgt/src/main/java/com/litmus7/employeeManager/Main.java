package com.litmus7.employeeManager;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "./src/main/resources/data/employees.csv";
       EmployeeManagerController file1 = new EmployeeManagerController();
       Map<String, Integer> result = new HashMap<>();
       try {
         result = file1.writeDataToDB(filePath);
       } catch (Exception e) {
        e.printStackTrace();
       }
      System.out.println(result);
    }

}