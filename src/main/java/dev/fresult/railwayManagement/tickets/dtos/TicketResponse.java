package dev.fresult.railwayManagement.tickets.dtos;

import dev.fresult.railwayManagement.tickets.Ticket;
import dev.fresult.railwayManagement.trainTrips.dtos.TrainTripResponse;
import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import java.util.Map;
import java.util.function.Function;

public record TicketResponse(
    int id,
    String seatNumber,
    Double price,
    TrainTripResponse trainTrip,
    UserInfoResponse passengerInfo) {

  public static TicketResponse fromTicketDao(Ticket ticket) {
    return new TicketResponse(ticket.id(), ticket.seatNumber(), null, null, null);
  }

  public static TicketResponse fromTicketDao(
      Ticket ticket, TrainTripResponse trainTrip, UserInfoResponse passengerInfo) {
    return new TicketResponse(
        ticket.id(), ticket.seatNumber(), trainTrip.price(), trainTrip, passengerInfo);
  }

  public static Function<Ticket, TicketResponse> fromTicketDaoWithTrainTripAndPassengerInfoMaps(
      Map<Integer, TrainTripResponse> trainTripMap,
      Map<Integer, UserInfoResponse> passengerInfoMap) {

    return ticket -> {
      var trip = trainTripMap.get(ticket.trainTripId().getId());
      System.out.println("trip = " + trip);
      return new TicketResponse(
          ticket.id(),
          ticket.seatNumber(),
          trip.price(),
          trip,
          passengerInfoMap.get(ticket.passengerId().getId()));
    };
  }
}
