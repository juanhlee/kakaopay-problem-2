package com.kakao.yebgi.server.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class CardExpiryDateValidator implements ConstraintValidator<CardExpiryDate, String> {
    @Override
    public void initialize(CardExpiryDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            try {
                return Optional
                        .ofNullable(
                                new SimpleDateFormat("mmYY")
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