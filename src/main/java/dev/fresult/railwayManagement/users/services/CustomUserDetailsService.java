package dev.fresult.railwayManagement.users.services;

import static org.springframework.security.core.userdetails.User.withUsername;

import dev.fresult.railwayManagement.users.entities.User;
import dev.fresult.railwayManagement.users.repositories.RoleRepository;
import dev.fresult.railwayManagement.users.repositories.UserRepository;
import dev.fresult.railwayManagement.users.repositories.UserRoleRepository;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final RoleRepository roleRepository;

  public CustomUserDetailsService(
      UserRepository userRepository,
      UserRoleRepository userRoleRepository,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    System.out.println("loadUserByUsername " + email);
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email));

    var userRoles = userRoleRepository.findByUserId(user.id());
    var roleIds =
        userRoles.stream().map(userRole -> userRole.roleId().getId()).collect(Collectors.toSet());
    var roles = roleRepository.findByIdIn(roleIds);

    System.out.println("User Details: " + user);

    return withUsername(user.email())
        .password(user.password())
        .authorities(
            roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList()))
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
