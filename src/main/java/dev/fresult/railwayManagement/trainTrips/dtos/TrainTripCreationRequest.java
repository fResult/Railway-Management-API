package dev.fresult.railwayManagement.trainTrips.dtos;

import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record TrainTripCreationRequest(
    @NotBlank String trainNumber,
    @NotNull Instant departureTime,
    @NotNull Instant arrivalTime,
    @NotNull Integer originalStationId,
    @NotNull Integer destinationStationId,
    @NotNull Double price) {

  public static TrainTrip dtoToTrainTripCreate(TrainTripCreationRequest body) {
    return new TrainTrip(
        null,
        body.trainNumber(),
        body.departureTime(),
        body.arrivalTime(),
        AggregateReference.to(body.originalStationId()),
        AggregateReference.to(body.destinationStationId()),
        body.price());
  }
}
