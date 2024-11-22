package dev.fresult.railwayManagement.trainTrips;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TrainTripRepository extends ListCrudRepository<TrainTrip, Integer> {
    List<TrainTrip> findByOriginStationIdAndDestinationStationId(Integer originStationId, Integer destinationStationId);

    List<TrainTrip> findByOriginStationId(Integer originStationId);

    List<TrainTrip> findByDestinationStationId(Integer destinationStationId);
}
