package dev.fresult.railwayManagement.trainTrips.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.stations.dtos.StationResponse;
import dev.fresult.railwayManagement.stations.services.StationService;
import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import dev.fresult.railwayManagement.trainTrips.TrainTripRepository;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripCreationRequest;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripResponse;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripUpdateRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TrainTripServiceImpl implements TrainTripService {
  private final Logger logger = LoggerFactory.getLogger(TrainTripServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(TrainTripServiceImpl.class);

  private final TrainTripRepository trainTripRepository;
  private final StationService stationService;

  public TrainTripServiceImpl(
      TrainTripRepository trainTripRepository, StationService stationService) {
    this.trainTripRepository = trainTripRepository;
    this.stationService = stationService;
  }

  @Override
  public List<TrainTripResponse> getTrainTrips(
      Integer originStationId, Integer destinationStationId) {
    logger.debug(
        "[getTrainTrips] Getting all train trips with originStationId [{}] and destinationStationId [{}]",
        originStationId,
        destinationStationId);

    var trainTrips = trainTripRepository.findAllWithFilters(originStationId, destinationStationId);
    var stationIdToStationMap = buildTrainTripIdToTrainTripMap(trainTrips);
    var toResponse = TrainTripResponse.fromTrainTripDaoWithStationMaps(stationIdToStationMap);

    return trainTrips.stream().map(toResponse).toList();
  }

  @Override
  public List<TrainTripResponse> getTrainTripsByIds(Set<Integer> trainTripIds) {
    logger.debug("[getTrainTripsByIds] Getting all {} by ids", TrainTrip.class.getSimpleName());
    var trainTrips = trainTripRepository.findAllById(trainTripIds);
    var stationIdToStationMap = buildTrainTripIdToTrainTripMap(trainTrips);
    var toResponse = TrainTripResponse.fromTrainTripDaoWithStationMaps(stationIdToStationMap);

    return trainTrips.stream().map(toResponse).toList();
  }

  @Override
  public TrainTripResponse getTrainTripById(int trainTripId) {
    logger.debug(
        "[getTrainTripById] Getting {} by id [{}]", TrainTrip.class.getSimpleName(), trainTripId);

    // NOTE: No N+1 issue here, because the `TrainTrip` will be only 1 or 0 item.
    return trainTripRepository
        .findById(trainTripId)
        .map(this::toResponseWithStations)
        .orElseThrow(errorHelper.entityNotFound("getTrainTripById", TrainTrip.class, trainTripId));
  }

  @Override
  public TrainTripResponse createTrainTrip(TrainTripCreationRequest body) {
    logger.debug("[createTrainTrip] Creating new {}", TrainTrip.class.getSimpleName());

    var trainTripToCreate = TrainTripCreationRequest.dtoToTrainTripCreate(body);
    var createdTrainTrip = trainTripRepository.save(trainTripToCreate);

    logger.info(
        "[createTrainTrip] New {} is created: {}",
        TrainTrip.class.getSimpleName(),
        createdTrainTrip);

    return toResponseWithStations(createdTrainTrip);
  }

  @Override
  public TrainTripResponse updateTrainTripById(int id, TrainTripUpdateRequest body) {
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

    return toResponseWithStations(updatedTrainTrip);
  }

  @Override
  public boolean deleteTrainTripById(int id) {
    logger.debug(
        "[deleteTrainTripById] Deleting {} by id [{}]", TrainTrip.class.getSimpleName(), id);
    var trainTripToDelete =
        trainTripRepository
            .findById(id)
            .orElseThrow(errorHelper.entityNotFound("deleteTrainTripById", TrainTrip.class, id));

    trainTripRepository.deleteById(trainTripToDelete.id());
    logger.info("[deleteTrainTripById] {} id [{}] is deleted", TrainTrip.class.getSimpleName(), id);

    return true;
  }

  private Set<Integer> buildTrainTripStationIds(List<TrainTrip> trainTrips) {
    Function<TrainTrip, Stream<Integer>> toTripStationId =
        trip -> {
          var originStationId = Objects.requireNonNull(trip.originStationId().getId());
          var destinationStationId = Objects.requireNonNull(trip.destinationStationId().getId());
          return Stream.of(originStationId, destinationStationId);
        };

    return trainTrips.stream().flatMap(toTripStationId).collect(Collectors.toSet());
  }

  private Map<Integer, StationResponse> buildTrainTripIdToTrainTripMap(List<TrainTrip> trainTrips) {
    var trainTripStationIds = buildTrainTripStationIds(trainTrips);
    /* NOTE: Use `getStationsByIds` and hash table to prevent 1+N issue. */
    var tripStations = stationService.getStationsByIds(trainTripStationIds);

    return tripStations.stream()
        .collect(Collectors.toMap(StationResponse::id, Function.identity()));
  }

  private TrainTripResponse toResponseWithStations(TrainTrip trainTrip) {
    var originStationId =
        Optional.ofNullable(trainTrip.originStationId().getId())
            // TODO: make it available for dynamic method name
            .orElseThrow(errorHelper.runtimeError("getTrainTripById", "OriginStationId is null"));
    var destinationStationId =
        Optional.ofNullable(trainTrip.destinationStationId().getId())
            // TODO: make it available for dynamic method name
            .orElseThrow(
                errorHelper.runtimeError("getTrainTripById", "DestinationStationId is null"));
    var originStation = stationService.getStationById(originStationId);
    var destinationStation = stationService.getStationById(destinationStationId);

    return TrainTripResponse.fromTrainTripDao(trainTrip, originStation, destinationStation);
  }
}
