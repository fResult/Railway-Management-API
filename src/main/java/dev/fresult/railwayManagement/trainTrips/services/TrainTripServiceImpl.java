package dev.fresult.railwayManagement.trainTrips.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import dev.fresult.railwayManagement.trainTrips.TrainTripRepository;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripCreationRequest;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class TrainTripServiceImpl implements TrainTripService {
  private final Logger logger = LoggerFactory.getLogger(TrainTripServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(TrainTripServiceImpl.class);

  private final TrainTripRepository trainTripRepository;

  public TrainTripServiceImpl(TrainTripRepository trainTripRepository) {
    this.trainTripRepository = trainTripRepository;
  }

  @Override
  public List<TrainTrip> getTrainTrips(Integer originStationId, Integer destinationStationId) {
    logger.debug(
        "[getTrainTrips] Getting all train trips with originStationId [{}] and destinationStationId [{}]",
        originStationId,
        destinationStationId);

    var optOriginStationId = Optional.ofNullable(originStationId);
    var optDestinationStationId = Optional.ofNullable(destinationStationId);

    if (optOriginStationId.isPresent() && optDestinationStationId.isPresent())
      return trainTripRepository.findByOriginStationIdAndDestinationStationId(
          originStationId, destinationStationId);

    if (optOriginStationId.isPresent())
      return trainTripRepository.findByOriginStationId(originStationId);

    if (optDestinationStationId.isPresent())
      return trainTripRepository.findByDestinationStationId(destinationStationId);

    return trainTripRepository.findAll();
  }

  @Override
  public TrainTrip getTrainTripById(int trainTripId) {
    logger.debug(
        "[getTrainTripById] Getting {} by id: {}", TrainTrip.class.getSimpleName(), trainTripId);

    return trainTripRepository
        .findById(trainTripId)
        .orElseThrow(errorHelper.entityNotFound("getTrainTripById", TrainTrip.class, trainTripId));
  }

  @Override
  public TrainTrip createTrainTrip(TrainTripCreationRequest body) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public TrainTrip updateTrainTripById(int id, TrainTripUpdateRequest body) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public boolean deleteTrainTripById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
