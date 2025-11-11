package untitled.travelerapi.Controller;

import untitled.travelerapi.DTO.*;
import untitled.travelerapi.Service.TravelPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/travel-plans")
public class TravelPlanController {
    @Autowired
    private TravelPlanService travelPlanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTravelPlan(@Valid @RequestBody CreateTravelPlanRequest request) {
        TravelPlanDetails createdPlan = travelPlanService.createPlan(request);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTravelPlanById(@PathVariable UUID id) {
        TravelPlanDetails plan = travelPlanService.getPlanDetails(id);
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTravelPlan(@PathVariable UUID id,
                                              @Valid @RequestBody UpdateTravelPlanRequest request) {
        TravelPlanDetails updatedPlan = travelPlanService.updatePlan(id, request);
        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTravelPlan(@PathVariable UUID id) {
        travelPlanService.deletePlan(id);
    }

    @PostMapping("/{id}/locations")
    public ResponseEntity<LocationResponse> addLocationToPlan(@PathVariable UUID id,
                                                              @Valid @RequestBody CreateLocationRequest request) {
        LocationResponse newLocation = travelPlanService.addLocationToPlan(id, request);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }
}
