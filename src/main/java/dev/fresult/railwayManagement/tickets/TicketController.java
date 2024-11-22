package dev.fresult.railwayManagement.tickets;

import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import dev.fresult.railwayManagement.tickets.services.TicketService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  private final Logger logger = LoggerFactory.getLogger(TicketController.class);
  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  public List<Ticket> getTickets(@RequestParam(name="passenger-id", required = false) Integer passengerId) {
    logger.debug("[getTickets] Getting all tickets");

    return ticketService.getTickets(passengerId);
  }

  @PatchMapping("/{id}")
  public Ticket updateTicket(
      @PathVariable int id, @Validated @RequestBody TicketUpdateRequest body) {

    var ticketToUpdate = TicketUpdateRequest.dtoToTicketUpdate(body);
    return ticketToUpdate.apply(new Ticket(id, null, null, null));
  }
}
