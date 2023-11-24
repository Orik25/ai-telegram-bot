package com.orik.adminapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserNameUniqueValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserName {
    public String value() default "";

    public String message() default "User with this username has already exist!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}