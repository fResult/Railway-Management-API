package dev.fresult.railwayManagement.trainTrips.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import dev.fresult.railwayManagement.trainTrips.TrainTripRepository;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripCreationRequest;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripUpdateRequest;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        "[getTrainTripById] Getting {} by id [{}]", TrainTrip.class.getSimpleName(), trainTripId);

    return trainTripRepository
        .findById(trainTripId)
        .orElseThrow(errorHelper.entityNotFound("getTrainTripById", TrainTrip.class, trainTripId));
  }

  @Override
  public TrainTrip createTrainTrip(TrainTripCreationRequest body) {
    logger.debug("[createTrainTrip] Creating new {}", TrainTrip.class.getSimpleName());

    var trainTripToCreate = TrainTripCreationRequest.dtoToTrainTripCreate(body);
    var createdTrainTrip = trainTripRepository.save(trainTripToCreate);

    logger.info(
        "[createTrainTrip] new {}: {} is created",
        TrainTrip.class.getSimpleName(),
        createdTrainTrip);

    return createdTrainTrip;
  }

  @Override
  public TrainTrip updateTrainTripById(int id, TrainTripUpdateRequest body) {
    logger.debug(
        "[updateTrainTripById] Updating {} by id [{}]", TrainTrip.class.getSimpleName(), id);
    var toTrainTripUpdate = TrainTripUpdateRequest.dtoToTrainTripUpdate(body);
    var trainTripToUpdate =
        trainTripRepository
            .findById(id)
            .map(toTrainTripUpdate)
            .orElseThrow(errorHelper.entityNotFound("updateTrainTripById", TrainTrip.class, id));

    var updatedTrainTrip = trainTripRepository.save(trainTripToUpdate);
    logger.info(
        "[updateTrainTripById] {} is updated: {}",
        TrainTrip.class.getSimpleName(),
        updatedTrainTrip);

    return updatedTrainTrip;
  }

  @Override
  public boolean deleteTrainTripById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
