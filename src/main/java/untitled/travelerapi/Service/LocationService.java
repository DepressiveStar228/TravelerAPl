package untitled.travelerapi.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import untitled.travelerapi.DTO.LocationResponse;
import untitled.travelerapi.DTO.UpdateLocationRequest;
import untitled.travelerapi.Entity.Location;
import untitled.travelerapi.Repository.LocationRepository;
import untitled.travelerapi.Mapper.TravelPlanMapper;

import java.util.UUID;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public LocationResponse updateLocation(UUID locationId, UpdateLocationRequest request) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + locationId));

        if (request.name() != null) location.setName(request.name());
        if (request.address() != null) location.setAddress(request.address());
        if (request.latitude() != null) location.setLatitude(request.latitude());
        if (request.longitude() != null) location.setLongitude(request.longitude());
        if (request.arrivalDate() != null) location.setArrivalDate(request.arrivalDate());
        if (request.departureDate() != null) location.setDepartureDate(request.departureDate());
        if (request.budget() != null) location.setBudget(request.budget());
        if (request.notes() != null) location.setNotes(request.notes());

        Location updatedLocation = locationRepository.save(location);
        return TravelPlanMapper.toLocationResponse(updatedLocation);
    }

    @Transactional
    public void deleteLocation(UUID locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new EntityNotFoundException("Location not found with id: " + locationId);
        }
        locationRepository.deleteById(locationId);
    }
}
