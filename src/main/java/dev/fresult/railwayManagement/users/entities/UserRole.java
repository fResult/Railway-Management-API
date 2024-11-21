package dev.fresult.railwayManagement.users.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
public record UserRole(
    @Id Integer id,
    AggregateReference<User, Integer> user,
    AggregateReference<Role, Integer> role) {}
