package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;
import dev.fresult.railwayManagement.users.dtos.UserUpdateRequest;

import java.util.List;

public interface UserService {
  List<UserInfoResponse> getUsers();

  UserInfoResponse getMyUserInfo();

  UserInfoResponse getUserById(int id);

  UserInfoResponse register(UserRegistrationRequest body);

  UserInfoResponse updateUserById(int id, UserUpdateRequest body);

  boolean deleteUserById(int id);
}
