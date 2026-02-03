package com.checkout.payment.gateway.model.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CurrencyValidationImpl.class)
public @interface CurrencyValidation {

  String message() default "Currency validation failed";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
