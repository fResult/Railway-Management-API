package dev.fresult.railwayManagement.tickets.services;

import dev.fresult.railwayManagement.tickets.Ticket;
import dev.fresult.railwayManagement.tickets.dtos.TicketCreationRequest;
import dev.fresult.railwayManagement.tickets.dtos.TicketResponse;
import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import java.util.List;

public interface TicketService {
  List<TicketResponse> getTickets(Integer passengerId);

  TicketResponse getTicketById(int ticketId);

  TicketResponse createTicket(TicketCreationRequest body);

  TicketResponse updateTicketById(int id, TicketUpdateRequest body);

  boolean deleteTicketById(int id);
}
