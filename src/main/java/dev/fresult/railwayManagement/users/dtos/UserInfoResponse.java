package dev.fresult.railwayManagement.users.dtos;

import dev.fresult.railwayManagement.users.entities.User;

public record UserInfoResponse(Integer id, String firstName, String lastName, String email) {
  public static UserInfoResponse fromUserDao(User user) {
    return new UserInfoResponse(user.id(), user.firstName(), user.lastName(), user.email());
  }
}
