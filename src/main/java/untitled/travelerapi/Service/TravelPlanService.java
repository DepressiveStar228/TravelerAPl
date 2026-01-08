package untitled.travelerapi.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import untitled.travelerapi.DTO.*;
import untitled.travelerapi.Entity.TravelPlan;
import untitled.travelerapi.Repository.TravelPlanRepository;
import untitled.travelerapi.Mapper.TravelPlanMapper;

import java.util.UUID;

@Service
public class TravelPlanService {
    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Transactional
    public TravelPlanDetails createPlan(CreateTravelPlanRequest request) {
        TravelPlan planToSave = TravelPlanMapper.toEntity(request);
        TravelPlan savedPlan = travelPlanRepository.saveAndFlush(planToSave);
        return TravelPlanMapper.toPlanDetails(savedPlan);
    }

    @Transactional(readOnly = true)
    public Page<TravelPlanDetails> getAllPlans(Pageable pageable) {
        return travelPlanRepository.findAll(pageable)
                .map(TravelPlanMapper::toPlanDetails);
    }

    @Transactional(readOnly = true)
    public TravelPlanDetails getPlanDetails(UUID id) {
        TravelPlan plan = travelPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Travel plan not found with id: " + id));
        return TravelPlanMapper.toPlanDetails(plan);
    }

    @Transactional
    public void deletePlan(UUID id) {
        if (!travelPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("Travel plan not found with id: " + id);
        }
        travelPlanRepository.deleteById(id);
    }

    @Transactional
    public TravelPlanDetails updatePlan(UUID id, UpdateTravelPlanRequest request) {
        try {
            TravelPlan plan = travelPlanRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Travel plan not found with id: " + id));

            if (!plan.getVersion().equals(request.version())) {
                throw new ObjectOptimisticLockingFailureException(
                        "Conflict: Travel plan was modified. Current version: " + plan.getVersion(),
                        null
                );
            }

            if (request.title() != null) {
                plan.setTitle(request.title());
            }
            if (request.description() != null) {
                plan.setDescription(request.description());
            }
            if (request.startDate() != null) {
                plan.setStartDate(request.startDate());
            }
            if (request.endDate() != null) {
                plan.setEndDate(request.endDate());
            }
            if (request.budget() != null) {
                plan.setBudget(request.budget());
            }
            if (request.currency() != null) {
                plan.setCurrency(request.currency());
            }
            if (request.isPublic() != null) {
                plan.setPublic(request.isPublic());
            }

            TravelPlan updatedPlan = travelPlanRepository.saveAndFlush(plan);
            return TravelPlanMapper.toPlanDetails(updatedPlan);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw e;
        }
    }

    @Transactional
    public LocationResponse addLocationToPlan(UUID planId, CreateLocationRequest request) {
        TravelPlan plan = travelPlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Travel plan not found"));

        TravelPlan.LocationData newLoc = TravelPlanMapper.toLocationData(request);
        plan.getLocations().add(newLoc);

        travelPlanRepository.save(plan);
        return TravelPlanMapper.toLocationResponse(newLoc, planId);
    }

    @Transactional
    public LocationResponse updateLocation(UUID locationId, UpdateLocationRequest request) {
        TravelPlan plan = travelPlanRepository.findByLocationId(locationId.toString())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        TravelPlan.LocationData loc = plan.getLocations().stream()
                .filter(l -> l.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Location data error"));

        if (request.name() != null) loc.setName(request.name());
        if (request.address() != null) loc.setAddress(request.address());
        if (request.latitude() != null) loc.setLatitude(request.latitude());
        if (request.longitude() != null) loc.setLongitude(request.longitude());
        if (request.arrivalDate() != null) loc.setArrivalDate(request.arrivalDate());
        if (request.departureDate() != null) loc.setDepartureDate(request.departureDate());
        if (request.budget() != null) loc.setBudget(request.budget());
        if (request.notes() != null) loc.setNotes(request.notes());

        travelPlanRepository.save(plan);
        return TravelPlanMapper.toLocationResponse(loc, plan.getId());
    }

    @Transactional
    public void deleteLocation(UUID locationId) {
        TravelPlan plan = travelPlanRepository.findByLocationId(locationId.toString())
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + locationId));
        boolean removed = plan.getLocations().removeIf(loc -> loc.getId().equals(locationId));

        if (!removed) {
            throw new EntityNotFoundException("Location could not be found in the plan's data");
        }

        travelPlanRepository.save(plan);
    }
}
