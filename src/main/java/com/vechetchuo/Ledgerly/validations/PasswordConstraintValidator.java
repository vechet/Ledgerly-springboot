package com.vechetchuo.Ledgerly.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * This class contains the actual logic for validating the password.
 */
class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    // This is a secure and common regex for password complexity.
    // (?=.*[0-9])       - a digit must occur at least once
    // (?=.*[a-z])       - a lower case letter must occur at least once
    // (?=.*[A-Z])       - an upper case letter must occur at least once
    // (?=.*[!@#$%^&*()]) - a special character must occur at least once
    // (?=\S+$)          - no whitespace allowed in the entire string
    // .{8,}             - anything, at least 8 characters long
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false; // Or true, if you allow null passwords (e.g., @Optional)
        }
        // Check if the password matches the regex
        return pattern.matcher(password).matches();
    }
}
