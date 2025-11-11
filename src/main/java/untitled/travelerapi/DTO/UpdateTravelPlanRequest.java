package untitled.travelerapi.DTO;

import jakarta.validation.constraints.*;
import untitled.travelerapi.DTO.Validation.ValidDateRange;

import java.math.BigDecimal;
import java.time.LocalDate;

@ValidDateRange
public record UpdateTravelPlanRequest(
        @NotBlank(message = "Title cannot be empty")
        @Size(max = 200)
        String title,

        String description,

        LocalDate startDate,

        LocalDate endDate,

        @Min(0)
        @Digits(integer=8, fraction=2, message="Budget must have up to 2 decimal places")
        BigDecimal budget,

        @Pattern(regexp = "^[A-Z]{3}$")
        String currency,

        Boolean isPublic,

        @NotNull(message = "Version is required for updates")
        @Min(1)
        Integer version
) {}
