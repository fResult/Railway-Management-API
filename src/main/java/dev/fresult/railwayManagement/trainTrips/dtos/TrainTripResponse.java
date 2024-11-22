package dev.fresult.railwayManagement.trainTrips.dtos;

import dev.fresult.railwayManagement.stations.dtos.StationResponse;
import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

public record TrainTripResponse(
    int id,
    String trainNumber,
    Instant departureTime,
    Instant arrivalTime,
    StationResponse originStation,
    StationResponse destinationStation,
    double price) {

  public static TrainTripResponse fromTrainTripDao(TrainTrip trainTrip) {
    return new TrainTripResponse(
        trainTrip.id(),
        trainTrip.trainNumber(),
        trainTrip.departureTime(),
        trainTrip.arrivalTime(),
        null,
        null,
        trainTrip.price());
  }

  public static TrainTripResponse fromTrainTripDao(
      TrainTrip trainTrip, StationResponse originStation, StationResponse destinationStation) {
    return new TrainTripResponse(
        trainTrip.id(),
        trainTrip.trainNumber(),
        trainTrip.departureTime(),
        trainTrip.arrivalTime(),
        originStation,
        destinationStation,
        trainTrip.price());
  }

  public static Function<TrainTrip, TrainTripResponse> fromTrainTripDaoWithStationMaps(
      Map<Integer, StationResponse> stationMap) {
    return trainTrip ->
        new TrainTripResponse(
            trainTrip.id(),
            trainTrip.trainNumber(),
            trainTrip.departureTime(),
            trainTrip.arrivalTime(),
            stationMap.get(trainTrip.originStationId().getId()),
            stationMap.get(trainTrip.destinationStationId().getId()),
            trainTrip.price());
  }
}
