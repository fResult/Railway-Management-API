package dev.fresult.railwayManagement.users.dtos;

import dev.fresult.railwayManagement.common.validations.NotEmptyIfPresent;
import dev.fresult.railwayManagement.users.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.util.Optional;
import java.util.function.Function;

public record UserUpdateRequest(
    @NotEmptyIfPresent @Schema(minLength = 6, maxLength = 255) @Size(min = 1, max = 255)
        String firstName,
    @NotEmptyIfPresent @Schema(minLength = 6, maxLength = 255) @Size(min = 1, max = 255)
        String lastName) {
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
