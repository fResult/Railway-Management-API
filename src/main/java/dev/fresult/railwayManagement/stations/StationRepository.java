package dev.fresult.railwayManagement.stations;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StationRepository extends ListCrudRepository<Station, Integer> {
  boolean existsByCode(String code);

  List<Station> findByIdIn(Collection<Integer> ids);
}
