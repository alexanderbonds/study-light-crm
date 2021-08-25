package ru.lightcrm.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lightcrm.controllers.interfaces.AuthController;
import ru.lightcrm.exceptions.LightCrmError;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.services.interfaces.UserService;
import ru.lightcrm.utils.JwtRequest;
import ru.lightcrm.utils.JwtResponse;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final UserService userService;

    @Override
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {

        try {
            JwtResponse jwtResponse = userService.createAuthToken(jwtRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException | NoSuchElementException ex) {
            log.warn("User with login: {} has incorrect username or password  ", jwtRequest.getUsername());
            return new ResponseEntity<>(
                    new LightCrmError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"),
                    HttpStatus.UNAUTHORIZED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(
                    new LightCrmError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}
