package dev.fresult.railwayManagement.tickets.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketCreationRequest(
    @NotNull Integer trainTripId,
    @NotNull Integer passengerId,
    @NotBlank String seatNumber) {}
