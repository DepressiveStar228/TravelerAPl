package untitled.travelerapi.DTO;

import jakarta.validation.constraints.*;
import untitled.travelerapi.DTO.Validation.ValidInstantRange;

import java.math.BigDecimal;
import java.time.Instant;

@ValidInstantRange
public record CreateLocationRequest(
        @NotBlank(message = "Title cannot be empty")
        @Size(max = 200)
        String name,

        String address,

        @DecimalMin("-90.0") @DecimalMax("90.0")
        BigDecimal latitude,

        @DecimalMin("-180.0") @DecimalMax("180.0")
        BigDecimal longitude,

        Instant arrivalDate,

        Instant departureDate,

        @Min(0)
        @Digits(integer=8, fraction=2, message="Budget must have up to 2 decimal places")
        BigDecimal budget,

        String notes
) { }
