package dev.fresult.railwayManagement.users.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("roles")
public record Role(@Id Integer id, String name) {}
