package dev.fresult.railwayManagement.common.helpers;

import java.util.Optional;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import static org.springframework.util.StringUtils.capitalize;

public class AggregateReferenceToUpdateBuilder {
  public static <T, ID> AggregateReference<T, ID> build(
      ID idFromRequest, ID idFromDb, String resourceName) {

    var existingId =
        Optional.ofNullable(idFromDb)
            .orElseThrow(
                () ->
                    new RuntimeException(String.format("%s id is null", capitalize(resourceName))));
    var idToUpdate = Optional.ofNullable(idFromRequest).orElse(existingId);

    return AggregateReference.to(idToUpdate);
  }
}
