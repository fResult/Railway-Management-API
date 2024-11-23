package dev.fresult.railwayManagement.users.repositories;

import dev.fresult.railwayManagement.users.entities.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {
  boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

  List<User> findByIdIn(Collection<Integer> ids);
}
