package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.users.dtos.*;

import java.util.Collection;
import java.util.List;

public interface UserService {
  List<UserInfoResponse> getUsers();

  List<UserInfoResponse> getUsersByIds(Collection<Integer> ids);

  UserInfoResponse getMyUserInfo();

  UserInfoResponse getUserById(int id);

  UserInfoResponse register(UserRegistrationRequest body);

  LoginResponse login(UserLoginRequest loginRequest);

  UserInfoResponse updateUserById(int id, UserUpdateRequest body);

  boolean deleteUserById(int id);
}
