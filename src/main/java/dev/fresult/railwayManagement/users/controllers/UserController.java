package dev.fresult.railwayManagement.users.controllers;

import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;
import dev.fresult.railwayManagement.users.dtos.UserUpdateRequest;
import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/api/users")
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserInfoResponse>> getUsers() {
    logger.debug("[getUsers] Getting all users");

    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping("/me")
  public ResponseEntity<UserInfoResponse> getMyUserInfo() {
    logger.debug("[getMyUserInfo] Getting my user info");

    return ResponseEntity.ok(userService.getMyUserInfo());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserInfoResponse> getUserById(@PathVariable int id) {
    logger.debug("[getUserById] Getting {} by id: {}", User.class.getSimpleName(), id);
    var user = userService.getUserById(id);

    return ResponseEntity.ok(user);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserInfoResponse> updateUserById(
      @PathVariable int id, @RequestBody UserUpdateRequest body) {
    logger.debug("[updateUserById] Updating {} by id: {}", User.class.getSimpleName(), id);

    return ResponseEntity.ok(userService.updateUserById(id, body));
  }

  @PostMapping
  public ResponseEntity<UserInfoResponse> register(@RequestBody UserRegistrationRequest body) {
    logger.debug("[register] new {} is registering", User.class.getSimpleName());
    var registeredUser = userService.register(body);
    var registeredUserUri = URI.create(String.format("/api/users/%d", registeredUser.id()));

    return ResponseEntity.created(registeredUserUri).body(registeredUser);
  }
}
