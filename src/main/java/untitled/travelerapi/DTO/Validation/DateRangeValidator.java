package untitled.travelerapi.DTO.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    private String startDateField = "startDate";
    private String endDateField = "endDate";

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object startDateObj = new BeanWrapperImpl(value).getPropertyValue(startDateField);
        Object endDateObj = new BeanWrapperImpl(value).getPropertyValue(endDateField);

        if (startDateObj == null || endDateObj == null) {
            return true;
        }

        LocalDate startDate = (LocalDate) startDateObj;
        LocalDate endDate = (LocalDate) endDateObj;

        return !endDate.isBefore(startDate);
    }
}
