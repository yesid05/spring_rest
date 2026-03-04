package co.spring.rest.error.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowercaseVal  implements ConstraintValidator<Lowercase, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        if(value == null)
            return true;

        return value.equals(value.toLowerCase());

    }



}
