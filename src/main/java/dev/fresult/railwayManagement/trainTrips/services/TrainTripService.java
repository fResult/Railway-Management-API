package dev.fresult.railwayManagement.trainTrips.services;

import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripCreationRequest;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripUpdateRequest;

import java.util.List;

public interface TrainTripService {
  List<TrainTrip> getTrainTrips(Integer originStationId, Integer destinationStationId);

  TrainTrip getTrainTripById(int trainTripId);

  TrainTrip createTrainTrip(TrainTripCreationRequest body);

  TrainTrip updateTrainTripById(TrainTripUpdateRequest body);

  boolean deleteTrainTripById(int id);
}
