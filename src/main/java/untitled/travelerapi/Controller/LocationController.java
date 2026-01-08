package untitled.travelerapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import untitled.travelerapi.DTO.LocationResponse;
import untitled.travelerapi.DTO.UpdateLocationRequest;
import untitled.travelerapi.Service.TravelPlanService;

import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private TravelPlanService travelPlanService;

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable UUID id,
                                                           @Valid @RequestBody UpdateLocationRequest request) {
        LocationResponse updatedLocation = travelPlanService.updateLocation(id, request);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable UUID id) {
        travelPlanService.deleteLocation(id);
    }
}
