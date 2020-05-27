package com.kakao.yebgi.server.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = CardExpiryDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CardExpiryDate {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}