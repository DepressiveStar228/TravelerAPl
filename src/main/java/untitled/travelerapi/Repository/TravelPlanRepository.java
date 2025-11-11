package untitled.travelerapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import untitled.travelerapi.Entity.TravelPlan;

import java.util.UUID;

@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlan, UUID> {

}
