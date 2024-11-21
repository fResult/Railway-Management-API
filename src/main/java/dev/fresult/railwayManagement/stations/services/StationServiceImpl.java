package dev.fresult.railwayManagement.stations.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.stations.StationRepository;
import dev.fresult.railwayManagement.stations.StationRequest;
import dev.fresult.railwayManagement.stations.StationResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {
  private final Logger logger = LoggerFactory.getLogger(StationServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(StationServiceImpl.class);

  private final StationRepository stationRepository;

  public StationServiceImpl(StationRepository stationRepository) {
    this.stationRepository = stationRepository;
  }

  @Override
  public List<StationResponse> getStations() {
    throw new UnsupportedOperationException("Not implemented yet");
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
}
