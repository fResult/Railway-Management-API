package dev.fresult.railwayManagement.common.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyIfPresentValidator implements ConstraintValidator<NotEmptyIfPresent, String> {

//  @Override
//  public void initialize(NotEmptyIfPresent constraintAnnotation) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || !value.trim().isEmpty();
  }
}
