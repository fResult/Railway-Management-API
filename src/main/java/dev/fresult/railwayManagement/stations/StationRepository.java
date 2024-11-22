package dev.fresult.railwayManagement.stations;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends ListCrudRepository<Station, Integer> {
  boolean existsByCode(String code);
}
