package dev.fresult.railwayManagement.stations.services;

import dev.fresult.railwayManagement.stations.dtos.StationCreationRequest;
import dev.fresult.railwayManagement.stations.dtos.StationResponse;
import dev.fresult.railwayManagement.stations.dtos.StationUpdateRequest;

import java.util.List;

public interface StationService {
  List<StationResponse> getStations();

  StationResponse getStationById(int id);

  StationResponse createStation(StationCreationRequest body);

  StationResponse updateStationById(int id, StationUpdateRequest body);

  boolean deleteStationById(int id);
}
