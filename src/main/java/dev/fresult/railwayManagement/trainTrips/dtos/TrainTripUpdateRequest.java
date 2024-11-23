package dev.fresult.railwayManagement.trainTrips.dtos;

import dev.fresult.railwayManagement.common.helpers.AggregateReferenceToUpdateBuilder;
import dev.fresult.railwayManagement.common.validations.NotEmptyIfPresent;
import dev.fresult.railwayManagement.stations.Station;
import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record TrainTripUpdateRequest(
    @NotEmptyIfPresent @Size(min = 1, max = 24) String trainNumber,
    Instant departureTime,
    Instant arrivalTime,
    @Min(1) Integer originalStationId,
    @Min(1) Integer destinationStationId,
    @Min(1) Double price) {
  public static Function<TrainTrip, TrainTrip> dtoToTrainTripUpdate(TrainTripUpdateRequest body) {
    return trainTrip -> {
      var originStationIdToUpdate =
          AggregateReferenceToUpdateBuilder.build(
              body.originalStationId(), trainTrip.originStationId(), "original station");
      var destinationStationIdToUpdate =
          AggregateReferenceToUpdateBuilder.build(
              body.destinationStationId(), trainTrip.destinationStationId(), "destination station");

      return new TrainTrip(
          trainTrip.id(),
          Optional.ofNullable(body.trainNumber()).orElse(trainTrip.trainNumber()),
          Optional.ofNullable(body.departureTime()).orElse(trainTrip.departureTime()),
          Optional.ofNullable(body.arrivalTime()).orElse(trainTrip.arrivalTime()),
          originStationIdToUpdate,
          destinationStationIdToUpdate,
          Optional.ofNullable(body.price).orElse(trainTrip.price()));
    };
  }

  private enum StationPoint {
    Origin,
    Destination
  }

  private static AggregateReference<Station, Integer> buildStationIdToUpdate(
      Integer stationIdRequest, Integer stationId, StationPoint point) {
    var existingStationId =
        Optional.ofNullable(stationId)
            .orElseThrow(
                () -> new RuntimeException(String.format("%s station id is null", point.name())));
    var stationIdToUpdate = Optional.ofNullable(stationIdRequest).orElse(existingStationId);
    return AggregateReference.to(stationIdToUpdate);
  }
}
