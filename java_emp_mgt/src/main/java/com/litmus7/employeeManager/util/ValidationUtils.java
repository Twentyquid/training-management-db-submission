package com.litmus7.employeeManager.util;
import com.litmus7.employeeManager.constant.Constants;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(Constants.EMAIL_REGEX);
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber != null && phoneNumber.matches(Constants.PHONE_REGEX);
    }

    public static boolean isValidDate(String dateString) {
        return dateString != null && dateString.matches(Constants.DATE_REGEX);  
    }
}
