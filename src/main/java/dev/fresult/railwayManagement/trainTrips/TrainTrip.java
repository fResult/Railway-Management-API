package dev.fresult.railwayManagement.trainTrips;

import dev.fresult.railwayManagement.stations.Station;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("train_trips")
public record TrainTrip(
    @Id Integer id,
    String trainNumber,
    Instant departureTime,
    Instant arrivalTime,
    AggregateReference<Station, Integer> originStationId,
    AggregateReference<Station, Integer> destinationStationId,
    double price) {
}

