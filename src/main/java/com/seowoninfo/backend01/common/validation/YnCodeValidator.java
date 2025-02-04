package com.seowoninfo.backend01.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.type.descriptor.sql.internal.Scale6IntervalSecondDdlType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-02-04
 * Description :
 */
public class YnCodeValidator implements ConstraintValidator<YnCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (value) {
            case "Y" -> true;
            case "N" -> true;
            default -> false;
        };
    }
}
