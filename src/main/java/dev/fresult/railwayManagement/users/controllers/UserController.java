package dev.fresult.railwayManagement.users.controllers;

import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;
import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController()
@RequestMapping("/api/users")
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String test() {
      return "Hello, world";
  }

  @PostMapping
  public ResponseEntity<UserInfoResponse> register(@RequestBody UserRegistrationRequest body) {
    logger.debug("[register] new {} is registering", User.class.getSimpleName());
    var registeredUser = userService.register(body);
    var registeredUserUri = URI.create(String.format("/api/users/%d", registeredUser.id()));

    return ResponseEntity.created(registeredUserUri).body(registeredUser);
  }
}
