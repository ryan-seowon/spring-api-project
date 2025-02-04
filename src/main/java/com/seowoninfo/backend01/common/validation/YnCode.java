package com.seowoninfo.backend01.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {YnCodeValidator.class})
@Documented
public @interface YnCode {

	String message() default "{validation.enum.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}