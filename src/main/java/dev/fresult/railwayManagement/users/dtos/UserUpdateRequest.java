package dev.fresult.railwayManagement.users.dtos;

import dev.fresult.railwayManagement.common.validations.NotEmptyIfPresent;
import dev.fresult.railwayManagement.users.entities.User;
import java.util.Optional;
import java.util.function.Function;

public record UserUpdateRequest(
        @NotEmptyIfPresent String firstName, @NotEmptyIfPresent String lastName) {
  public static Function<User, User> dtoToUserUpdate(UserUpdateRequest body) {
    return user ->
        new User(
            user.id(),
            Optional.ofNullable(body.firstName()).orElse(user.firstName()),
            Optional.ofNullable(body.lastName()).orElse(user.lastName()),
            user.email(),
            user.password());
  }
}
