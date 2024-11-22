package dev.fresult.railwayManagement.trainTrips;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface TrainTripRepository extends ListCrudRepository<TrainTrip, Integer> {
  @Query(
      """
      SELECT * FROM train_trips tt
      WHERE (COALESCE(:originStationId, NULL) IS NULL OR tt.origin_station_id = :originStationId)
        AND (COALESCE(:destinationStationId, NULL) IS NULL OR tt.destination_station_id = :destinationStationId)
      """)
  List<TrainTrip> findAllWithFilters(Integer originStationId, Integer destinationStationId);
}
