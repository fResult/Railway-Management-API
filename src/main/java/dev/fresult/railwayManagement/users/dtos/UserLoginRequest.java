package dev.fresult.railwayManagement.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(@Email String email, @NotBlank String password) {}
