package dev.fresult.railwayManagement.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
    @NotBlank @Schema(minLength = 1, maxLength = 255) String firstName,
    @NotBlank @Schema(minLength = 1, maxLength = 255) String lastName,
    @NotBlank @Schema(format = "email") @Email String email,
    @NotBlank @Schema(minLength = 6, maxLength = 255) @Size(min = 6, max = 255) String password) {}
