package dev.fresult.railwayManagement.tickets.services;

import dev.fresult.railwayManagement.tickets.Ticket;
import dev.fresult.railwayManagement.tickets.dtos.TicketCreationRequest;
import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import java.util.List;

public interface TicketService {
  List<Ticket> getTickets(Integer passengerId);

  Ticket getTicketById(int ticketId);

  Ticket createTicket(TicketCreationRequest body);

  Ticket updateTicketById(TicketUpdateRequest body);

  boolean deleteTicketById(int id);
}
