package dev.fresult.railwayManagement.trainTrips.services;

import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripCreationRequest;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripResponse;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripUpdateRequest;
import java.util.List;
import java.util.Set;

public interface TrainTripService {
  List<TrainTripResponse> getTrainTrips(Integer originStationId, Integer destinationStationId);

  List<TrainTripResponse> getTrainTripsByIds(Set<Integer> trainTripIds);

  TrainTripResponse getTrainTripById(int trainTripId);

  TrainTripResponse createTrainTrip(TrainTripCreationRequest body);

  TrainTripResponse updateTrainTripById(int id, TrainTripUpdateRequest body);

  boolean deleteTrainTripById(int id);
}
