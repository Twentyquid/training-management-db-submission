package com.example;


public class Main {
    public static void main(String[] args) {
        String filePath = "./src/main/java/com/example/data/employees.csv";
       EmployeeManagerController.checkConnection();
       EmployeeManagerController file1 = new EmployeeManagerController(filePath);
       try {
         file1.writeDataToDB();
       } catch (Exception e) {
        e.printStackTrace();
       }
      
    }

}