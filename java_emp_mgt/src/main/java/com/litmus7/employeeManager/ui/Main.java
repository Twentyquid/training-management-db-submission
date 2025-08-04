package com.litmus7.employeeManager.ui;
import com.litmus7.employeeManager.constant.StatusCodes;

import java.util.Map;
import com.litmus7.employeeManager.dto.Response;
import com.litmus7.employeeManager.controller.EmployeeManagerController;

public class Main {
    public static void main(String[] args) {
        String filePath = "./src/main/resources/data/employees.csv";
       EmployeeManagerController file1 = new EmployeeManagerController();

       Response<Map<String, Integer>> result = file1.writeDataToDB(filePath);
       if(result.getStatusCode() == StatusCodes.SUCCESS) {
           System.out.println("Data processed successfully: " + result.getData());
       } else {
           System.out.println("Error: " + result.getMessage());
       }
    }

}