package untitled.travelerapi.DTO.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import java.time.Instant;

public class InstantRangeValidator implements ConstraintValidator<ValidInstantRange, Object> {
    private String arrivalDateField = "arrivalDate";
    private String departureDateField = "departureDate";

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object arrivalDateObj = new BeanWrapperImpl(value).getPropertyValue(arrivalDateField);
        Object departureDateObj = new BeanWrapperImpl(value).getPropertyValue(departureDateField);

        if (arrivalDateObj == null || departureDateObj == null) {
            return true;
        }

        Instant arrivalDate = (Instant) arrivalDateObj;
        Instant departureDate = (Instant) departureDateObj;

        return !departureDate.isBefore(arrivalDate);
    }
}
