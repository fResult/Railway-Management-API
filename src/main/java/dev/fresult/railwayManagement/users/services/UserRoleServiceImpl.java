package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.users.entities.UserRole;
import dev.fresult.railwayManagement.users.repositories.UserRoleRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
  private final UserRoleRepository userRoleRepository;

  public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
    this.userRoleRepository = userRoleRepository;
  }

  @Override
  public UserRole saveUserRole(int userId, int roleId) {
    var userRole = new UserRole(null, AggregateReference.to(userId), AggregateReference.to(roleId));
    return userRoleRepository.save(userRole);
  }
}
