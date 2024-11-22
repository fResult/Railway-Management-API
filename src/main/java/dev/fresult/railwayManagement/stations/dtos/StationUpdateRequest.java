package dev.fresult.railwayManagement.stations.dtos;

import dev.fresult.railwayManagement.common.helpers.AggregateReferenceToUpdateBuilder;
import dev.fresult.railwayManagement.stations.Station;
import java.util.Optional;
import java.util.function.Function;

public record StationUpdateRequest(String code, String name, String location, Integer contactId) {

  public static Function<Station, Station> dtoToStationUpdate(StationUpdateRequest body) {

    return station -> {
      var contactIdToUpdate =
          AggregateReferenceToUpdateBuilder.build(body.contactId(), station.contactId());

      return new Station(
          station.id(),
          Optional.ofNullable(body.code()).orElse(station.code()),
          Optional.ofNullable(body.name()).orElse(station.name()),
          Optional.ofNullable(body.location()).orElse(station.location()),
          contactIdToUpdate);
    };
  }
}
