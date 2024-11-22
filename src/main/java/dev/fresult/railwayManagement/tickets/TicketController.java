package dev.fresult.railwayManagement.tickets;

import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  private final Logger logger = LoggerFactory.getLogger(TicketController.class);

  @PatchMapping("/{id}")
  public Ticket updateTicket(
      @PathVariable int id, @Validated @RequestBody TicketUpdateRequest body) {

    var ticketToUpdate = TicketUpdateRequest.dtoToTicketUpdate(body);
    return ticketToUpdate.apply(new Ticket(id, null, null, null));
  }
}
