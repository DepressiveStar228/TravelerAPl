package untitled.travelerapi.DTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TravelPlanDetails(
        UUID id,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal budget,
        String currency,
        boolean isPublic,
        Integer version,
        Instant createdAt,
        Instant updatedAt,

        List<LocationResponse> locations
) {}
