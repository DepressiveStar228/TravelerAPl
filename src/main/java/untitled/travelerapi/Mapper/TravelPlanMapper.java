package untitled.travelerapi.Mapper;

import untitled.travelerapi.DTO.CreateLocationRequest;
import untitled.travelerapi.DTO.CreateTravelPlanRequest;
import untitled.travelerapi.DTO.LocationResponse;
import untitled.travelerapi.DTO.TravelPlanDetails;
import untitled.travelerapi.Entity.TravelPlan;

import java.util.UUID;
import java.util.stream.Collectors;

public class TravelPlanMapper {
    public static TravelPlan toEntity(CreateTravelPlanRequest dto) {
        TravelPlan entity = new TravelPlan();
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        entity.setBudget(dto.budget());
        entity.setCurrency(dto.currency() != null ? dto.currency() : "USD");
        entity.setPublic(dto.isPublic() != null ? dto.isPublic() : false);

        return entity;
    }

    public static LocationResponse toLocationResponse(TravelPlan.LocationData data, UUID planId) {
        return new LocationResponse(
                data.getId(),
                planId,
                data.getName(),
                data.getAddress(),
                data.getLatitude(),
                data.getLongitude(),
                null,
                data.getArrivalDate(),
                data.getDepartureDate(),
                data.getBudget(),
                data.getNotes(),
                null,
                null
        );
    }

    public static TravelPlanDetails toPlanDetails(TravelPlan entity) {
        return new TravelPlanDetails(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getBudget(),
                entity.getCurrency(),
                entity.isPublic(),
                entity.getVersion(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getLocations().stream()
                        .map(loc -> toLocationResponse(loc, entity.getId()))
                        .collect(Collectors.toList())
        );
    }

    public static TravelPlan.LocationData toLocationData(CreateLocationRequest dto) {
        return new TravelPlan.LocationData(
                UUID.randomUUID(),
                dto.name(),
                dto.address(),
                dto.latitude(),
                dto.longitude(),
                dto.arrivalDate(),
                dto.departureDate(),
                dto.budget(),
                dto.notes()
        );
    }
}

