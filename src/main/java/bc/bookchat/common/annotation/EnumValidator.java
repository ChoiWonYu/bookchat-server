package bc.bookchat.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
    @Override
    public void initialize(ValidEnum constraint) {

    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null;
    }
}
