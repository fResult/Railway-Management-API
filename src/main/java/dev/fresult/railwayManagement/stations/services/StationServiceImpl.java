package dev.fresult.railwayManagement.stations.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.stations.Station;
import dev.fresult.railwayManagement.stations.StationRepository;
import dev.fresult.railwayManagement.stations.dtos.StationRequest;
import dev.fresult.railwayManagement.stations.dtos.StationResponse;
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
    var stations = stationRepository.findAll();
    var contactIds = buildStationContactIds(stations);
    var contacts = userService.getUsersByIds(contactIds);
    var contactIdToContactMap =
        contacts.stream().collect(Collectors.toMap(UserInfoResponse::id, Function.identity()));
    var toResponse = StationResponse.fromStationDaoWithContactMap(contactIdToContactMap);

    return stations.stream().map(toResponse).toList();
  }

  @Override
  public StationResponse getStationById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public StationResponse createStation(StationRequest stationRequest) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public StationResponse updateStationById(int id, StationRequest stationRequest) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public boolean deleteStationById(int id) {
    logger.debug(
        "[deleteStationById] Deleting {} with id: {}", StationResponse.class.getSimpleName(), id);
    stationRepository.deleteById(id);

    return true;
  }

  private Set<Integer> buildStationContactIds(List<Station> stations) {
    return stations.stream()
        .map(station -> station.contactId().getId())
        .collect(Collectors.toSet());
  }
}
