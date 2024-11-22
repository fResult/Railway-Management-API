package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.common.enums.RoleName;
import dev.fresult.railwayManagement.common.exceptions.CredentialExistsException;
import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;
import dev.fresult.railwayManagement.users.dtos.UserUpdateRequest;
import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.repositories.UserRepository;
import java.util.Collection;
import java.util.List;
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
    userRoleService.saveUserRole(registeredUser.id(), RoleName.PASSENGER.getId());
    logger.info("[register] New {}: {} is registered", User.class.getSimpleName(), registeredUser);

    return UserInfoResponse.fromUserDao(registeredUser);
  }

  @Override
  public List<UserInfoResponse> getUsers() {
    logger.debug("[getUsers] Getting all {}s", User.class.getSimpleName());
    return userRepository.findAll().stream().map(UserInfoResponse::fromUserDao).toList();
  }

  @Override
  public UserInfoResponse getMyUserInfo() {
    // TODO: Find by token then get the user info
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public UserInfoResponse getUserById(int id) {
    logger.debug("[getUserById] Getting {} by id: [{}]", User.class.getSimpleName(), id);
    return userRepository
        .findById(id)
        .map(UserInfoResponse::fromUserDao)
        .orElseThrow(errorHelper.entityNotFound("getUserById", User.class, 888));
  }

  @Override
  public List<UserInfoResponse> getUsersByIds(Collection<Integer> ids) {
    logger.debug("[getUsersByIds] Getting {}s by ids: [{}]", User.class.getSimpleName(), ids);
    return userRepository.findByIdIn(ids).stream().map(UserInfoResponse::fromUserDao).toList();
  }

  @Override
  public UserInfoResponse updateUserById(int id, UserUpdateRequest body) {
    logger.debug("[updateUser] Updating {} id: [{}]", User.class.getSimpleName(), id);
    var toUserUpdate = UserUpdateRequest.dtoToUserUpdate(body);
    var userToUpdate =
        userRepository
            .findById(id)
            .map(toUserUpdate)
            .orElseThrow(errorHelper.entityNotFound("updateUser", User.class, id));
    var updatedUser = userRepository.save(userToUpdate);
    logger.info("[updateUser] {} is updated: {}", User.class.getSimpleName(), updatedUser);

    return UserInfoResponse.fromUserDao(updatedUser);
  }

  @Override
  public boolean deleteUserById(int id) {
    logger.debug("[deleteUser] Deleting {} id [{}]", User.class.getSimpleName(), id);
    var userToDelete =
        userRepository
            .findById(id)
            .orElseThrow(errorHelper.entityNotFound("deleteUser", User.class, id));

    userRepository.delete(userToDelete);
    logger.info("[deleteUser] {} id [{}] is deleted", User.class.getSimpleName(), id);

    return true;
  }
}
