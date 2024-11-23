package dev.fresult.railwayManagement.tickets.dtos;

import dev.fresult.railwayManagement.tickets.Ticket;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record TicketCreationRequest(
    @NotNull @Min(1) Integer trainTripId,
    @NotNull @Min(1) Integer passengerId,
    @NotBlank @Size(min = 1, max = 16) String seatNumber) {

  public static Ticket dtoToTicketCreate(TicketCreationRequest body) {
    return new Ticket(
        null,
        AggregateReference.to(body.trainTripId()),
        AggregateReference.to(body.passengerId()),
        body.seatNumber());
  }
}
