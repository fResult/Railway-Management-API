package dev.fresult.railwayManagement.tickets;

import dev.fresult.railwayManagement.tickets.dtos.TicketUpdateRequest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
  @PatchMapping("/{id}")
  public Ticket updateTicket(
      @PathVariable int id, @Validated @RequestBody TicketUpdateRequest body) {

    var ticketToUpdate = TicketUpdateRequest.dtoToTicketUpdate(body);
    return ticketToUpdate.apply(new Ticket(id, null, null, null));
  }
}
