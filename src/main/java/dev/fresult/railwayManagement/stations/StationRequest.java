package dev.fresult.railwayManagement.stations;

import dev.fresult.railwayManagement.users.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record StationRequest(
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String location,
        @NotNull Integer contact_id) {}
