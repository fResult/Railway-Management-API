package dev.fresult.railwayManagement.stations.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.stations.Station;
import dev.fresult.railwayManagement.stations.StationRepository;
import dev.fresult.railwayManagement.stations.dtos.StationCreationRequest;
import dev.fresult.railwayManagement.stations.dtos.StationResponse;
import dev.fresult.railwayManagement.stations.dtos.StationUpdateRequest;
import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {
  private final Logger logger = LoggerFactory.getLogger(StationServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(StationServiceImpl.class);

  private final StationRepository stationRepository;
  private final UserService userService;

  public StationServiceImpl(StationRepository stationRepository, UserService userService) {
    this.stationRepository = stationRepository;
    this.userService = userService;
  }

  @Override
  public List<StationResponse> getStations() {
    logger.debug("[getStations] Getting all {}", StationResponse.class.getSimpleName());
    var stations = stationRepository.findAll();
    var contactIds = buildStationContactIds(stations);
    /* NOTE: Use `getUsersByIds` and HashMap to prevent 1+N issue. */
    var contacts = userService.getUsersByIds(contactIds);
    var contactIdToContactMap =
        contacts.stream().collect(Collectors.toMap(UserInfoResponse::id, Function.identity()));
    var toResponse = StationResponse.fromStationDaoWithContactMap(contactIdToContactMap);

    return stations.stream().map(toResponse).toList();
  }

  @Override
  public StationResponse getStationById(int id) {
    logger.debug("[getStationById] Getting {} id [{}]", StationResponse.class.getSimpleName(), id);
    return stationRepository
        .findById(id)
        .map(this::toResponseWithContact)
        .orElseThrow(errorHelper.entityNotFound("getStationById", Station.class, id));
  }

  @Override
  public StationResponse createStation(StationCreationRequest body) {
    logger.debug("[createStation] Creating new {}", Station.class.getSimpleName());
    var toStationCreate = StationCreationRequest.dtoToStationCreate(body);
    var createdStation = stationRepository.save(toStationCreate);
    logger.info(
        "[createStation] New {} is created: {}", Station.class.getSimpleName(), createdStation);

    return toResponseWithContact(createdStation);
  }

  @Override
  public StationResponse updateStationById(int id, StationUpdateRequest body) {
    logger.debug("[updateStationById] Updating {} id: [{}]", Station.class.getSimpleName(), id);
    var toStationUpdate = StationUpdateRequest.dtoToStationUpdate(body);
    var stationToUpdate =
        stationRepository
            .findById(id)
            .map(toStationUpdate)
            .orElseThrow(errorHelper.entityNotFound("getStationById", Station.class, id));

    var updatedStation = stationRepository.save(stationToUpdate);
    logger.info(
        "[updateStationById] {} is updated: {}", Station.class.getSimpleName(), updatedStation);

    return toResponseWithContact(updatedStation);
  }

  @Override
  public boolean deleteStationById(int id) {
    logger.debug(
        "[deleteStationById] Deleting {} with id: {}", StationResponse.class.getSimpleName(), id);
    stationRepository.deleteById(id);
    logger.info(
        "[deleteStationById] {} id [{}] is deleted", StationResponse.class.getSimpleName(), id);

    return true;
  }

  private Set<Integer> buildStationContactIds(List<Station> stations) {
    return stations.stream()
        .map(station -> station.contactId().getId())
        .collect(Collectors.toSet());
  }

  private StationResponse toResponseWithContact(Station station) {
    var contactId =
        Optional.ofNullable(station.contactId().getId())
            .orElseThrow(errorHelper.runtimeError("getStationById", "Contact Id is null"));
    var contact = userService.getUserById(contactId);
    return StationResponse.fromStationDao(station, contact);
  }
}
