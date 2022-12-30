package com.justedlev.storage.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotEmptyMultipartFileValidator.class)
public @interface NotEmptyMultipartFile {
    String message() default "File cannot be empty.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
