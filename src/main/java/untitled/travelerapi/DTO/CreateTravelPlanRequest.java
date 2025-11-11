package untitled.travelerapi.DTO;

import jakarta.validation.constraints.*;
import untitled.travelerapi.DTO.Validation.ValidDateRange;

import java.math.BigDecimal;
import java.time.LocalDate;

@ValidDateRange
public record CreateTravelPlanRequest(
        @NotBlank(message = "Title cannot be empty")
        @Size(max = 200)
        String title,

        String description,

        LocalDate startDate,

        LocalDate endDate,

        Boolean isPublic,

        @Min(value = 0, message = "Budget cannot be negative")
        @Digits(integer=8, fraction=2, message="Budget must have up to 2 decimal places")
        BigDecimal budget,

        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter uppercase code")
        String currency
) {}
