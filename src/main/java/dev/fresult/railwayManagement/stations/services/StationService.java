package dev.fresult.railwayManagement.stations.services;

import dev.fresult.railwayManagement.stations.dtos.StationCreationRequest;
import dev.fresult.railwayManagement.stations.dtos.StationResponse;
import dev.fresult.railwayManagement.stations.dtos.StationUpdateRequest;
import java.util.Collection;
import java.util.List;

public interface StationService {
  List<StationResponse> getStations();

  List<StationResponse> getStationsByIds(Collection<Integer> ids);

  StationResponse getStationById(int id);

  StationResponse createStation(StationCreationRequest body);

  StationResponse updateStationById(int id, StationUpdateRequest body);

  boolean deleteStationById(int id);
}
