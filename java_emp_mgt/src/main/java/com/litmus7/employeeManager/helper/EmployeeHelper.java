package com.litmus7.employeeManager.helper;

import java.util.Set;

import com.litmus7.employeeManager.util.ValidationUtils;

public class EmployeeHelper {
    public static boolean isValidEmployeeRecord(String[] row, Set<String> processedEmpIDs) {
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
}
