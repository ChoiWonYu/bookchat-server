package bc.bookchat.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues == null) return false;
        if (value == null) return false;
        for (Object enumValue : enumValues) {
            if (value == enumValue) {
                result = true;
                break;
            }
        }
        return result;
    }
}
