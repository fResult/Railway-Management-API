package dev.fresult.railwayManagement.users;

import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;
import dev.fresult.railwayManagement.users.dtos.UserUpdateRequest;
import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.services.UserService;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController()
@RequestMapping("/api/users")
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @GetMapping
  public ResponseEntity<List<UserInfoResponse>> getUsers() {
    logger.debug("[getUsers] Getting all {}s", User.class.getSimpleName());

    return ResponseEntity.ok(userService.getUsers());
  }

  // TODO: need to have token to get information
  @GetMapping("/me")
  public ResponseEntity<UserInfoResponse> getMyUserInfo() {
    logger.debug("[getMyUserInfo] Getting my {} info", User.class.getSimpleName());

    return ResponseEntity.ok(userService.getMyUserInfo());
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @GetMapping("/{id}")
  public ResponseEntity<UserInfoResponse> getUserById(@Min(1) @PathVariable int id) {
    logger.debug("[getUserById] Getting {} by id [{}]", User.class.getSimpleName(), id);
    var user = userService.getUserById(id);

    return ResponseEntity.ok(user);
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @PostMapping
  public ResponseEntity<UserInfoResponse> register(
      @Validated @RequestBody UserRegistrationRequest body) {
    logger.debug("[register] Registering new {}", User.class.getSimpleName());
    var registeredUser = userService.register(body);
    var registeredUserUri = URI.create(String.format("/api/users/%d", registeredUser.id()));

    return ResponseEntity.created(registeredUserUri).body(registeredUser);
  }

  // TODO: Protected route for logged in user
  @PatchMapping("/{id}")
  public ResponseEntity<UserInfoResponse> updateUserById(
      @Min(1) @PathVariable int id, @Validated @RequestBody UserUpdateRequest body) {
    logger.debug("[updateUserById] Updating {} by id [{}]", User.class.getSimpleName(), id);

    return ResponseEntity.ok(userService.updateUserById(id, body));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUserById(@Min(1) @PathVariable int id) {
    logger.debug("[deleteUserById] Deleting {} by id: {}", User.class.getSimpleName(), id);
    userService.deleteUserById(id);

    return ResponseEntity.ok(
        String.format("Delete %s id [%d] successfully", User.class.getSimpleName(), id));
  }
}
