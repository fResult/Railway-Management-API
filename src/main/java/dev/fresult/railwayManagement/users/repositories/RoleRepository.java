package dev.fresult.railwayManagement.users.repositories;

import dev.fresult.railwayManagement.users.entities.Role;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends ListCrudRepository<Role, Integer> {
  List<Role> findByIdIn(Collection<Integer> ids);
}
