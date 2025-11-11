package untitled.travelerapi.Mapper;

import untitled.travelerapi.DTO.CreateLocationRequest;
import untitled.travelerapi.DTO.CreateTravelPlanRequest;
import untitled.travelerapi.DTO.LocationResponse;
import untitled.travelerapi.DTO.TravelPlanDetails;
import untitled.travelerapi.Entity.Location;
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

    public static Location toLocationEntity(CreateLocationRequest dto) {
        Location entity = new Location();
        entity.setName(dto.name());
        entity.setAddress(dto.address());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        entity.setArrivalDate(dto.arrivalDate());
        entity.setDepartureDate(dto.departureDate());
        entity.setBudget(dto.budget());
        entity.setNotes(dto.notes());
        return entity;
    }

    public static LocationResponse toLocationResponse(Location entity) {
        UUID planId = (entity.getTravelPlan() != null) ? entity.getTravelPlan().getId() : null;

        return new LocationResponse(
                entity.getId(),
                planId,
                entity.getName(),
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getVisitOrder(),
                entity.getArrivalDate(),
                entity.getDepartureDate(),
                entity.getBudget(),
                entity.getNotes(),
                entity.getCreatedAt()
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
                        .map(TravelPlanMapper::toLocationResponse)
                        .collect(Collectors.toList())
        );
    }
}
