ALTER TABLE travel_plans ADD COLUMN locations JSONB DEFAULT '[]'::jsonb;

UPDATE travel_plans tp
SET locations = (
    SELECT jsonb_agg(jsonb_build_object(
            'id', l.id,
            'name', l.name,
            'address', l.address,
            'latitude', l.latitude,
            'longitude', l.longitude,
            'arrivalDate', l.arrival_date,
            'departureDate', l.departure_date,
            'budget', l.budget,
            'notes', l.notes
                     ))
    FROM locations l
    WHERE l.travel_plan_id = tp.id
);

CREATE INDEX idx_travel_plans_locations ON travel_plans USING GIN (locations);