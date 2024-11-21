package dev.fresult.railwayManagement.stations;

import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.entities.User;

import java.util.function.Function;

public record StationResponse(
    int id, String code, String name, String location, UserInfoResponse contact_info) {
  public static StationResponse fromStationDao(Station station) {
    return new StationResponse(
        station.id(), station.code(), station.name(), station.location(), null);
  }

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
