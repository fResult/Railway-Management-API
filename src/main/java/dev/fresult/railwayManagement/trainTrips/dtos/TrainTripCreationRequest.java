package dev.fresult.railwayManagement.trainTrips.dtos;

import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record TrainTripCreationRequest(
    @NotBlank @Size(min = 1, max = 24) String trainNumber,
    @NotNull @Min(1) Instant departureTime,
    @NotNull @Min(1) Instant arrivalTime,
    @NotNull @Min(1) Integer originalStationId,
    @NotNull @Min(1) Integer destinationStationId,
    @NotNull @Min(1) Double price) {

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
