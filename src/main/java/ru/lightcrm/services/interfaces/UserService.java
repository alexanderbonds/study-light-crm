package ru.lightcrm.services.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lightcrm.entities.User;
import ru.lightcrm.entities.dtos.UserDto;
import ru.lightcrm.utils.JwtRequest;
import ru.lightcrm.utils.JwtResponse;

public interface UserService extends UserDetailsService {
    UserDto getDtoByUsername(String username);
    User getByUsername(String username);
    void saveUser(User user);
    boolean isPresent(String login);
    JwtResponse createAuthToken(JwtRequest jwtRequest);
}
