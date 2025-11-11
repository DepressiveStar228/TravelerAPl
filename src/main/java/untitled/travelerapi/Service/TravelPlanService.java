package untitled.travelerapi.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import untitled.travelerapi.DTO.*;
import untitled.travelerapi.Entity.Location;
import untitled.travelerapi.Entity.TravelPlan;
import untitled.travelerapi.Repository.LocationRepository;
import untitled.travelerapi.Repository.TravelPlanRepository;
import untitled.travelerapi.Mapper.TravelPlanMapper;

import java.util.UUID;

@Service
public class TravelPlanService {
    @Autowired
    private TravelPlanRepository travelPlanRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public TravelPlanDetails createPlan(CreateTravelPlanRequest request) {
        TravelPlan planToSave = TravelPlanMapper.toEntity(request);
        TravelPlan savedPlan = travelPlanRepository.saveAndFlush(planToSave);
        return TravelPlanMapper.toPlanDetails(savedPlan);
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
                .orElseThrow(() -> new EntityNotFoundException("Travel plan not found with id: " + planId));

        Location newLocation = TravelPlanMapper.toLocationEntity(request);
        newLocation.setTravelPlan(plan);
        Location savedLocation = locationRepository.saveAndFlush(newLocation);

        return TravelPlanMapper.toLocationResponse(savedLocation);
    }
}
