package com.kakao.yebgi.server.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    private String format;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            try {
                return Optional
                        .ofNullable(
                                new SimpleDateFormat(format)
                                        .parse(value)
                        )
                        .map(date -> true)
                        .get();
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}