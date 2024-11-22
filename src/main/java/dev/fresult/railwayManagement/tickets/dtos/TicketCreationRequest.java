package dev.fresult.railwayManagement.tickets.dtos;

import dev.fresult.railwayManagement.tickets.Ticket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record TicketCreationRequest(
    @NotNull Integer trainTripId, @NotNull Integer passengerId, @NotBlank String seatNumber) {

  public static Ticket dtoToTicketCreate(TicketCreationRequest body) {
    return new Ticket(
        null,
        AggregateReference.to(body.trainTripId()),
        AggregateReference.to(body.passengerId()),
        body.seatNumber());
  }
}
