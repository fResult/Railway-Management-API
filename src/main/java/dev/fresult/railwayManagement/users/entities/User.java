package dev.fresult.railwayManagement.users.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
    @Id Integer id, String firstName, String lastName, String email, String password) {}
