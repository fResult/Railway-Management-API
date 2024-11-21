package dev.fresult.railwayManagement.stations.services;

import dev.fresult.railwayManagement.stations.dtos.StationRequest;
import dev.fresult.railwayManagement.stations.dtos.StationResponse;

import java.util.List;

public interface StationService {
  List<StationResponse> getStations();

  StationResponse getStationById(int id);

  StationResponse createStation(StationRequest stationRequest);

  StationResponse updateStationById(int id, StationRequest stationRequest);

  boolean deleteStationById(int id);
}
