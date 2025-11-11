package untitled.travelerapi.DTO;

import jakarta.validation.constraints.*;
import untitled.travelerapi.DTO.Validation.ValidInstantRange;

import java.math.BigDecimal;
import java.time.Instant;

@ValidInstantRange
public record UpdateLocationRequest(
        @Size(max = 200)
        String name,

        String address,

        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
        BigDecimal latitude,

        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
        BigDecimal longitude,

        Instant arrivalDate,
        Instant departureDate,

        @Min(value = 0, message = "Budget cannot be negative")
        @Digits(integer=8, fraction=2, message="Budget must have up to 2 decimal places")
        BigDecimal budget,

        String notes
) { }
