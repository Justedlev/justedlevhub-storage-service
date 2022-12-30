package com.justedlev.storage.common.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyMultipartFileValidator implements ConstraintValidator<NotEmptyMultipartFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return file.getSize() > 0;
    }
}
