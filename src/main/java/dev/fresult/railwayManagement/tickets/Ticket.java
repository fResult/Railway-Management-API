package dev.fresult.railwayManagement.tickets;

import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import dev.fresult.railwayManagement.users.entities.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Ticket(
    @Id Integer id,
    AggregateReference<TrainTrip, Integer> trainTripId,
    AggregateReference<User, Integer> passengerId,
    String seatNumber) {}
