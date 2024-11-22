package dev.fresult.railwayManagement.tickets.dtos;

import dev.fresult.railwayManagement.tickets.Ticket;
import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import dev.fresult.railwayManagement.users.entities.User;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.Optional;
import java.util.function.Function;

public record TicketUpdateRequest(
    AggregateReference<TrainTrip, Integer> trainTripId,
    AggregateReference<User, Integer> passengerId,
    String seatNumber) {

    public static Function<Ticket, Ticket> dtoToTicketUpdate(TicketUpdateRequest body) {
        return ticket -> new Ticket(
            ticket.id(),
            Optional.ofNullable(body.trainTripId()).orElse(ticket.trainTripId()),
            Optional.ofNullable(body.passengerId()).orElse(ticket.passengerId()),
            Optional.ofNullable(body.seatNumber()).orElse(ticket.seatNumber())
        );
    }
}
