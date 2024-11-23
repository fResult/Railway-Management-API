package dev.fresult.railwayManagement.stations.dtos;

import dev.fresult.railwayManagement.stations.Station;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record StationCreationRequest(
    @NotBlank @Size(min = 1, max = 16) String code,
    @NotBlank @Size(min = 1, max = 255) String name,
    @NotBlank @Size(min = 3, max = 255) String location,
    @NotNull @Min(1) Integer contactId) {

  public static Station dtoToStationCreate(StationCreationRequest body) {
    return new Station(
        null, body.code(), body.name(), body.location(), AggregateReference.to(body.contactId()));
  }
}
