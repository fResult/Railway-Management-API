package dev.fresult.railwayManagement.users.dtos;

import dev.fresult.railwayManagement.users.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserInfoResponse(
    Integer id, String firstName, String lastName, @Schema(format = "email") String email) {
  public static UserInfoResponse fromUserDao(User user) {
    return new UserInfoResponse(user.id(), user.firstName(), user.lastName(), user.email());
  }
}
