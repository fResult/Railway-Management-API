package dev.fresult.railwayManagement.stations.services;

import dev.fresult.railwayManagement.stations.StationRequest;
import dev.fresult.railwayManagement.stations.StationResponse;

import java.util.List;

public interface StationService {
  List<StationResponse> getStations();

  StationResponse getStationById(int id);

  StationResponse createStation(StationRequest stationRequest);

  StationResponse updateStationById(int id, StationRequest stationRequest);

  boolean deleteStationById(int id);
}
