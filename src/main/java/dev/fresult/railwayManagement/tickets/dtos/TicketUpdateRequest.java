package dev.fresult.railwayManagement.tickets.dtos;

import dev.fresult.railwayManagement.common.helpers.AggregateReferenceToUpdateBuilder;
import dev.fresult.railwayManagement.common.validations.NotEmptyIfPresent;
import dev.fresult.railwayManagement.tickets.Ticket;
import dev.fresult.railwayManagement.trainTrips.TrainTrip;
import jakarta.validation.constraints.Min;
import java.util.Optional;
import java.util.function.Function;

public record TicketUpdateRequest(
    @Min(1) Integer trainTripId,
    @Min(1) Integer passengerId,
    @NotEmptyIfPresent String seatNumber) {

  public static Function<Ticket, Ticket> dtoToTicketUpdate(TicketUpdateRequest body) {
    return ticket -> {
      var trainTripIdToUpdate =
          AggregateReferenceToUpdateBuilder.build(
              body.trainTripId, ticket.trainTripId(), TrainTrip.class.getSimpleName());
      var passengerIdToUpdate =
          AggregateReferenceToUpdateBuilder.build(
              body.passengerId, ticket.passengerId(), "passenger");

      return new Ticket(
          ticket.id(),
          trainTripIdToUpdate,
          passengerIdToUpdate,
          Optional.ofNullable(body.seatNumber()).orElse(ticket.seatNumber()));
    };
  }
}
