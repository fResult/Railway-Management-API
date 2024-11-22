package dev.fresult.railwayManagement.stations.dtos;

import dev.fresult.railwayManagement.stations.Station;
import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.entities.User;
import java.util.Map;
import java.util.function.Function;

public record StationResponse(
    int id, String code, String name, String location, UserInfoResponse contactInfo) {
  public static StationResponse fromStationDao(Station station) {
    return new StationResponse(
        station.id(), station.code(), station.name(), station.location(), null);
  }

  public static StationResponse fromStationDao(Station station, UserInfoResponse contact) {
    return new StationResponse(
        station.id(), station.code(), station.name(), station.location(), contact);
  }

  public static Function<Station, StationResponse> fromStationDaoWithContactMap(
      Map<Integer, UserInfoResponse> contactMap) {
    return station ->
        new StationResponse(
            station.id(),
            station.code(),
            station.name(),
            station.location(),
            contactMap.get(station.contactId().getId()));
  }

  // TODO: Remove it if not used
  public static Function<User, StationResponse> withContactInfo(StationResponse self) {
    return contact ->
        new StationResponse(
            self.id(),
            self.code(),
            self.name(),
            self.location(),
            UserInfoResponse.fromUserDao(contact));
  }
}
