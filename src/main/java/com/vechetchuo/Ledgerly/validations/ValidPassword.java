package com.vechetchuo.Ledgerly.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * This is the annotation. You will use @ValidPassword on your field.
 */
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class) // Links to the logic
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    // Default error message
    String message() default "Invalid password: Must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, and one special character.";

    // Required by validation framework
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}