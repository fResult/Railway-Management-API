package dev.fresult.railwayManagement.users.services;

import dev.fresult.railwayManagement.common.exceptions.CredentialExistsException;
import dev.fresult.railwayManagement.common.helpers.ErrorHelper;
import dev.fresult.railwayManagement.users.dtos.UserInfoResponse;
import dev.fresult.railwayManagement.users.dtos.UserRegistrationRequest;
import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.repositories.UserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  private final ErrorHelper errorHelper = new ErrorHelper(UserServiceImpl.class);

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserInfoResponse register(UserRegistrationRequest body) {
    logger.debug("[register] new {} is registering", User.class.getSimpleName());
    System.out.println("register start");
    var isEmailExisted = userRepository.existsByEmail(body.email());
    System.out.println("after checking email: " + isEmailExisted);
    if (isEmailExisted) {
      logger.warn(
          "[register] {} email: {} is already existed", User.class.getSimpleName(), body.email());
      throw new CredentialExistsException("Email is already existed");
    }

    var encryptedPassword = passwordEncoder.encode(body.password());
    System.out.println("Password en: " + encryptedPassword);

    var userToRegister =
        new User(null, body.firstName(), body.lastName(), body.email(), encryptedPassword);
    var registeredUser = userRepository.save(userToRegister);
    System.out.println("register end: " + registeredUser);

    return Optional.of(registeredUser).map(UserInfoResponse::fromUserDao).get();
  }

  @Override
  public UserInfoResponse getUserById(int id) {
    return userRepository
        .findById(id)
        .map(UserInfoResponse::fromUserDao)
        .orElseThrow(errorHelper.entityNotFound("getUserById", User.class, 888));
  }
}
