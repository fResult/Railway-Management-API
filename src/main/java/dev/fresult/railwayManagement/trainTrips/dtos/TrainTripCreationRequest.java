package dev.fresult.railwayManagement.trainTrips.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record TrainTripCreationRequest(
    @NotBlank String trainNumber,
    @NotNull Instant departureTime,
    @NotNull Instant arrivalTime,
    @NotNull Integer originalStationId,
    @NotNull Integer destinationStationId) {}
