package dev.fresult.railwayManagement.stations.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StationRequest(
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String location,
        @NotNull Integer contact_id) {}
