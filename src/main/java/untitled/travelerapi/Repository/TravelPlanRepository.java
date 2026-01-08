package untitled.travelerapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import untitled.travelerapi.Entity.TravelPlan;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlan, UUID> {
    @Query(value = "SELECT * FROM travel_plans WHERE locations @> CAST(json_build_array(jsonb_build_object('id', :locationId)) AS jsonb)", nativeQuery = true)
    Optional<TravelPlan> findByLocationId(@Param("locationId") String locationId);
}
