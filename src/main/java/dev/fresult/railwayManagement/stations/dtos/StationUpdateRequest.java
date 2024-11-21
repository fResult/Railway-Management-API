package dev.fresult.railwayManagement.stations.dtos;

import dev.fresult.railwayManagement.stations.Station;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record StationUpdateRequest(String code, String name, String location, Integer contactId) {

  public static Function<Station, Station> dtoToUserUpdate(StationUpdateRequest body) {

    return station -> {
      var existingStationContactId =
          Optional.ofNullable(station.contactId().getId())
              .orElseThrow(() -> new RuntimeException("Contact id is null"));
      var contactIdToUpdate =
          Optional.ofNullable(body.contactId()).orElse(existingStationContactId);

      return new Station(
          station.id(),
          Optional.ofNullable(body.code()).orElse(station.code()),
          Optional.ofNullable(body.name()).orElse(station.name()),
          Optional.ofNullable(body.location()).orElse(station.location()),
          AggregateReference.to(contactIdToUpdate));
    };
  }
}
