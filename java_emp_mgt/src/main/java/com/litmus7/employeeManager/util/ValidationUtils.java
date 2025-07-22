package com.litmus7.employeeManager.util;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        String phoneRegex = "^(\\+91-)?[6-9]\\d{9}$";
        return phoneNumber != null && phoneNumber.matches(phoneRegex);
    }

    public static boolean isValidDate(String dateString) {
        String dateregex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        return dateString != null && dateString.matches(dateregex);
    }
}
