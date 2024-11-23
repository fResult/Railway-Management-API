package dev.fresult.railwayManagement.users;

import dev.fresult.railwayManagement.users.dtos.*;
import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController()
@RequestMapping("/api/users")
@SecurityRequirement(name = "basicAuth")
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'STATION_STAFF')")
  public ResponseEntity<List<UserInfoResponse>> getUsers() {
    logger.debug("[getUsers] Getting all {}s", User.class.getSimpleName());

    return ResponseEntity.ok(userService.getUsers());
  }

  // TODO: need to have token to get information
  @Operation(hidden = true)
  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<UserInfoResponse> getMyUserInfo() {
    System.out.println("getMyUserInfo /me");
    logger.debug("[getMyUserInfo] Getting my {} info", User.class.getSimpleName());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String email = userDetails.getUsername();
    logger.info("[getMyUserInfo] Getting {} info by email: {}", User.class.getSimpleName(), email);

    return ResponseEntity.ok(userService.getMyUserInfo());
  }

  // TODO: Protected route for only ADMIN and STATION_STAFF
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'STATION_STAFF')")
  public ResponseEntity<UserInfoResponse> getUserById(@Min(1) @PathVariable int id) {
    logger.debug("[getUserById] Getting {} by id [{}]", User.class.getSimpleName(), id);
    var user = userService.getUserById(id);

    return ResponseEntity.ok(user);
  }

  @PostMapping("/register")
  public ResponseEntity<UserInfoResponse> register(
      @Validated @RequestBody UserRegistrationRequest body) {
    logger.debug("[register] Registering new {}", User.class.getSimpleName());
    var registeredUser = userService.register(body);
    var registeredUserUri = URI.create(String.format("/api/users/%d", registeredUser.id()));

    return ResponseEntity.created(registeredUserUri).body(registeredUser);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest loginRequest) {
    logger.debug("[login] Logging in with email: [{}]", loginRequest.email());

    return ResponseEntity.ok(userService.login(loginRequest));
  }

  // TODO: Protected route for logged in user
  @PatchMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'STATION_STAFF')")
  public ResponseEntity<UserInfoResponse> updateUserById(
      @Parameter(schema = @Schema(type = "integer", minimum = "1")) @Min(1) @PathVariable int id,
      @Validated @RequestBody UserUpdateRequest body) {
    logger.debug("[updateUserById] Updating {} by id [{}]", User.class.getSimpleName(), id);

    var userToken = userService.updateUserById(id, body);

    return ResponseEntity.ok(userToken);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'STATION_STAFF')")
  public ResponseEntity<String> deleteUserById(@Min(1) @PathVariable int id) {
    logger.debug("[deleteUserById] Deleting {} by id: {}", User.class.getSimpleName(), id);
    userService.deleteUserById(id);

    return ResponseEntity.ok(
        String.format("Delete %s id [%d] successfully", User.class.getSimpleName(), id));
  }
}
