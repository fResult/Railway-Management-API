package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.users.entities.UserRole;

public interface UserRoleService {
  UserRole saveUserRole(int userId, int roleId);
}
