package dev.fresult.railwayManagement.tickets.services;

import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.tickets.Ticket;
import dev.fresult.railwayManagement.tickets.TicketRepository;
import dev.fresult.railwayManagement.tickets.dtos.TicketCreationRequest;
import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
  private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(TicketServiceImpl.class);

  private final TicketRepository ticketRepository;

  public TicketServiceImpl(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  public List<Ticket> getTickets(Integer passengerId) {
    logger.debug("[getTickets] Getting all {}s", Ticket.class.getSimpleName());

    return ticketRepository.findAllWithFilters(passengerId);
  }

  @Override
  public Ticket getTicketById(int ticketId) {
    logger.debug("[getTicketById] Getting {} id [{}]", Ticket.class.getSimpleName(), ticketId);

    return ticketRepository
        .findById(ticketId)
        .orElseThrow(errorHelper.entityNotFound("getTicketById", Ticket.class, ticketId));
  }

  @Override
  public Ticket createTicket(TicketCreationRequest body) {
    logger.debug("[createTicket] Creating new {}", Ticket.class.getSimpleName());

    var ticketToCreate = TicketCreationRequest.dtoToTicketCreate(body);
    var createdTicket = ticketRepository.save(ticketToCreate);
    logger.info(
        "[createTicket] New {} is created: {}", Ticket.class.getSimpleName(), createdTicket);

    return createdTicket;
  }

  @Override
  public Ticket updateTicketById(int id, TicketUpdateRequest body) {
    logger.debug("[updateTicketById] Updating {} id [{}]", Ticket.class.getSimpleName(), id);

    var toTicketUpdate = TicketUpdateRequest.dtoToTicketUpdate(body);
    var ticketToUpdate =
        ticketRepository
            .findById(id)
            .orElseThrow(errorHelper.entityNotFound("updateTicketById", Ticket.class, id));

    var updatedTicket = ticketRepository.save(toTicketUpdate.apply(ticketToUpdate));
    logger.info(
        "[updateTicketById] {} is updated: {}", Ticket.class.getSimpleName(), updatedTicket);

    return updatedTicket;
  }

  @Override
  public boolean deleteTicketById(int id) {
    logger.debug("[deleteTicketById] Deleting {} id [{}]", Ticket.class.getSimpleName(), id);

    throw new UnsupportedOperationException("Not implemented yet");
  }
}
