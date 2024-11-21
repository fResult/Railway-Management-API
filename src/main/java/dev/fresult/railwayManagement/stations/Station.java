package dev.fresult.railwayManagement.stations;

import dev.fresult.railwayManagement.users.entities.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("stations")
public record Station(
    @Id Integer id,
    String code,
    String name,
    String location,
    AggregateReference<User, Integer> contactId) {}
