package dev.fresult.railwayManagement.users.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String email,
    @NotBlank String password) {}
