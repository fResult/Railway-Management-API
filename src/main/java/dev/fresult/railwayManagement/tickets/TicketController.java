package dev.fresult.railwayManagement.tickets;

import dev.fresult.railwayManagement.tickets.dtos.TicketCreationRequest;
import dev.fresult.railwayManagement.tickets.dtos.TicketResponse;
import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import dev.fresult.railwayManagement.tickets.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;
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

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @GetMapping
  public ResponseEntity<List<TicketResponse>> getTickets(
      @RequestParam(name = "passenger-id", required = false) @Min(1) Integer passengerId) {

    logger.debug("[getTickets] Getting all {}s", Ticket.class.getSimpleName());

    return ResponseEntity.ok(ticketService.getTickets(passengerId));
  }

  // TODO: need to have token to get information
  @Operation(hidden = true)
  @GetMapping("/me")
  public ResponseEntity<List<TicketResponse>> getMyTickets() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @GetMapping("/{id}")
  public ResponseEntity<TicketResponse> getTicketById(@PathVariable int id) {
    logger.debug("[getTicketById] Getting {} by id [{}]", Ticket.class.getSimpleName(), id);

    return ResponseEntity.ok(ticketService.getTicketById(id));
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @PostMapping
  public ResponseEntity<TicketResponse> createTicket(@Validated @RequestBody TicketCreationRequest body) {
    logger.debug("[createTicket] Creating new {}", Ticket.class.getSimpleName());
    var createdTicket = ticketService.createTicket(body);
    var uri = URI.create(String.format("/api/tickets/%d", createdTicket.id()));

    return ResponseEntity.created(uri).body(createdTicket);
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @PatchMapping("/{id}")
  public TicketResponse updateTicket(
      @Min(1) @PathVariable int id, @Validated @RequestBody TicketUpdateRequest body) {

    return ticketService.updateTicketById(id, body);
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTicketById(@PathVariable int id) {
    logger.debug("[deleteTicketById] Deleting {} id [{}]", Ticket.class.getSimpleName(), id);

    return ResponseEntity.ok(
        String.format("Delete %s by id [%d] successfully", Ticket.class.getSimpleName(), id));
  }
}
