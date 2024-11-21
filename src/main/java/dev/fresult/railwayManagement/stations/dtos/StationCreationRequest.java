package dev.fresult.railwayManagement.stations.dtos;

import dev.fresult.railwayManagement.stations.Station;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record StationCreationRequest(
    @NotBlank String code,
    @NotBlank String name,
    @NotBlank String location,
    @NotNull Integer contactId) {
  public static Station dtoToStationCreate(StationCreationRequest body) {
    return new Station(
        null, body.code(), body.name(), body.location(), AggregateReference.to(body.contactId()));
  }
}
