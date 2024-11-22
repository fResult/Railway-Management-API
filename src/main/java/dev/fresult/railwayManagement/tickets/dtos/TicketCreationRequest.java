package dev.fresult.railwayManagement.tickets.dtos;

import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import dev.fresult.railwayManagement.users.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record TicketCreationRequest(
    @NotNull AggregateReference<TrainTrip, Integer> trainTripId,
    @NotNull AggregateReference<User, Integer> passengerId,
    @NotBlank String seatNumber) {}
