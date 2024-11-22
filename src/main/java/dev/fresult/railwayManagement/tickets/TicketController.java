package dev.fresult.railwayManagement.tickets;

import dev.fresult.railwayManagement.tickets.dtos.TicketCreationRequest;
import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import dev.fresult.railwayManagement.tickets.services.TicketService;

import java.net.URI;
import java.util.List;

import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  private final Logger logger = LoggerFactory.getLogger(TicketController.class);
  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  public ResponseEntity<List<Ticket>> getTickets(
      @RequestParam(name = "passenger-id", required = false)
          @Min(1)
          Integer passengerId) {

    logger.debug("[getTickets] Getting all {}s", Ticket.class.getSimpleName());

    return ResponseEntity.ok(ticketService.getTickets(passengerId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
    logger.debug("[getTicketById] Getting {} by id [{}]", Ticket.class.getSimpleName(), id);

    return ResponseEntity.ok(ticketService.getTicketById(id));
  }

  @PostMapping
  public ResponseEntity<Ticket> createTicket(@Validated @RequestBody TicketCreationRequest body) {
    logger.debug("[createTicket] Creating new {}", Ticket.class.getSimpleName());
    var createdTicket = ticketService.createTicket(body);
    var uri = URI.create(String.format("/api/tickets/%d", createdTicket.id()));

    return ResponseEntity.created(uri).body(createdTicket);
  }

  @PatchMapping("/{id}")
  public Ticket updateTicket(
      @Min(1) @PathVariable int id, @Validated @RequestBody TicketUpdateRequest body) {

    var ticketToUpdate = TicketUpdateRequest.dtoToTicketUpdate(body);
    return ticketToUpdate.apply(new Ticket(id, null, null, null));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTicketById(@PathVariable int id) {
    logger.debug("[deleteTicketById] Deleting {} id [{}]", Ticket.class.getSimpleName(), id);

    return ResponseEntity.ok(
        String.format("Delete %s by id [%d] successfully", Ticket.class.getSimpleName(), id));
  }
}
