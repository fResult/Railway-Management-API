package dev.fresult.railwayManagement.users.repositories;

import dev.fresult.railwayManagement.users.entities.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {
  boolean existsByEmail(String email);
}
