package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;

public interface UserService {
  UserInfoResponse register(UserRegistrationRequest body);

  UserInfoResponse getUserById(int id);
}
