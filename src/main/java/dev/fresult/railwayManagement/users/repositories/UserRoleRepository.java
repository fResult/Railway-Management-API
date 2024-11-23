package dev.fresult.railwayManagement.users.repositories;

import dev.fresult.railwayManagement.users.entities.UserRole;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRoleRepository extends ListCrudRepository<UserRole, Integer> {
  Optional<UserRole> findByUserId(Integer userId);
}
