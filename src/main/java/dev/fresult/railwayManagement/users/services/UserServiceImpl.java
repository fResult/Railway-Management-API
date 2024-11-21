package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.common.enums.RoleName;
import dev.fresult.railwayManagement.common.exceptions.CredentialExistsException;
import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;
import dev.fresult.railwayManagement.users.dtos.UserUpdateRequest;
import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(UserServiceImpl.class);

  private final UserRepository userRepository;
  private final UserRoleService userRoleService;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(
      UserRepository userRepository,
      UserRoleService userRoleService,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userRoleService = userRoleService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public UserInfoResponse register(UserRegistrationRequest body) {
    logger.debug("[register] new {} is registering", User.class.getSimpleName());

    var isEmailExisted = userRepository.existsByEmail(body.email());
    if (isEmailExisted) {
      logger.warn(
          "[register] {} email: {} is already existed", User.class.getSimpleName(), body.email());
      throw new CredentialExistsException(
          String.format(
              "%s email [%s] is already existed", User.class.getSimpleName(), body.email()));
    }

    var encryptedPassword = passwordEncoder.encode(body.password());
    var userToRegister =
        new User(null, body.firstName(), body.lastName(), body.email(), encryptedPassword);
    var registeredUser = userRepository.save(userToRegister);
    var addedUserRole =
        userRoleService.saveUserRole(registeredUser.id(), RoleName.PASSENGER.getId());
    logger.info("[register] new {}: {} is registered", User.class.getSimpleName(), registeredUser);

    return Optional.of(registeredUser).map(UserInfoResponse::fromUserDao).get();
  }

  @Override
  public List<UserInfoResponse> getUsers() {
    return userRepository.findAll().stream().map(UserInfoResponse::fromUserDao).toList();
  }

  @Override
  public UserInfoResponse getMyUserInfo() {
    // TODO: Find by token then get the user info
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public UserInfoResponse getUserById(int id) {
    return userRepository
        .findById(id)
        .map(UserInfoResponse::fromUserDao)
        .orElseThrow(errorHelper.entityNotFound("getUserById", User.class, 888));
  }

  @Override
  public UserInfoResponse updateUserById(int id, UserUpdateRequest body) {
    var bodyToUserUpdate = toUserUpdate(body);
    var userToUpdate =
        userRepository
            .findById(id)
            .map(bodyToUserUpdate)
            .orElseThrow(errorHelper.entityNotFound("updateUser", User.class, id));
    var updatedUser = userRepository.save(userToUpdate);
    logger.info("[updateUser] {}: {} is updated", User.class.getSimpleName(), updatedUser);

    return Optional.of(updatedUser).map(UserInfoResponse::fromUserDao).get();
  }

  @Override
  public boolean deleteUserById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  // FIXME: rename this method to be easier to understand
  private Function<User, User> toUserUpdate(UserUpdateRequest body) {
    return user ->
        new User(
            user.id(),
            Optional.ofNullable(body.firstName()).orElse(user.firstName()),
            Optional.ofNullable(body.lastName()).orElse(user.lastName()),
            user.email(),
            user.password());
  }
}
