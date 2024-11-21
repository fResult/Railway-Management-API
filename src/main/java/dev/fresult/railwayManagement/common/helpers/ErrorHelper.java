package dev.fresult.railwayManagement.common.helpers;

import dev.fresult.railwayManagement.common.exceptions.EntityNotFoundException;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHelper {
  private final Logger logger;

  public ErrorHelper(Class<?> classToLog) {
    logger = LoggerFactory.getLogger(classToLog);
  }

  public Supplier<EntityNotFoundException> entityNotFound(
      String methodName, Class<?> entityClass, Integer id) {
    return () -> {
      logger.warn("[{}] {} id: {} not found", methodName, entityClass.getSimpleName(), id);
      return new EntityNotFoundException(
          String.format("%s id [%s] not found", entityClass.getSimpleName(), id));
    };

  }

  public Supplier<RuntimeException> runtimeError(String methodName, String message) {
    return () -> {
      logger.error("[{}] {}", methodName, message);
      return new RuntimeException(message);
    };
  }
}
