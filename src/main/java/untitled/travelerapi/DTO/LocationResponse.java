package untitled.travelerapi.DTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record LocationResponse(
        UUID id,
        UUID travelPlanId,
        String name,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer visitOrder,
        Instant arrivalDate,
        Instant departureDate,
        BigDecimal budget,
        String notes,
        Instant createdAt
) {}
