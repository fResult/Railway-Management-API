package dev.fresult.railwayManagement.tickets;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface TicketRepository extends ListCrudRepository<Ticket, Integer> {
  @Query("SELECT * FROM tickets t WHERE COALESCE(:passengerId, NULL) IS NULL OR t.passenger_id = :passengerId")
  List<Ticket> findAllWithFilters(Integer passengerId);
}
