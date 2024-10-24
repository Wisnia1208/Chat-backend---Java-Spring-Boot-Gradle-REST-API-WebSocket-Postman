package pl.polsl.rest.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.rest.api.dto.*;
import pl.polsl.rest.api.service.HttpSessionService;
import pl.polsl.rest.api.service.UserService;
import pl.polsl.rest.api.service.WebsocketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    private final HttpSessionService sessionService;

    public UsersController(final UserService userService, final HttpSessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/register")
    ResponseEntity<RegisterDto> handleRegister(@RequestBody final AuthDto authDto) {
        if (!isUserDetailsNotEmpty(authDto)) {
            return ResponseEntity.ok(new RegisterDto(false));
        }
        Optional<UserDataDto> userDataDto = userService.register(authDto);

        if (userDataDto.isPresent()) {
            return ResponseEntity.ok(new RegisterDto(true));
        }
        return ResponseEntity.ok(new RegisterDto(false));
    }


    @PostMapping("/login")
    ResponseEntity<LoggedInDto> handleLogin(@RequestBody final AuthDto authDto, final HttpSession session) {

        return ResponseEntity.ok(new LoggedInDto(userService.login(authDto,session).isPresent()));
    }

    // TODO: 4
    @GetMapping("/logout")
    ResponseEntity<LoggedInDto> handleLogout(final HttpSession session) {

        userService.logout(session);
        return ResponseEntity.ok(new LoggedInDto(false));
    }

    @GetMapping("/user")
    ResponseEntity<LoggedInDto> handleUser(final HttpSession session) {
        if (this.sessionService.isLoggedIn(session)) {
            return ResponseEntity.ok(new LoggedInDto(sessionService.isLoggedIn(session), this.sessionService.getUserName(session), this.sessionService.getUserId(session)));
        }
        return ResponseEntity.ok(new LoggedInDto(sessionService.isLoggedIn(session)));
    }

    @GetMapping("")
    ResponseEntity<?> handleGetAllUsers(final HttpSession session) {
        if (this.sessionService.isLoggedIn(session)) {
            return ResponseEntity.ok(this.userService.getUsers());
        }
        return ResponseEntity.ok(new LoggedInDto(false));
    }

    private boolean isUserDetailsNotEmpty(final AuthDto userDetails) {
        final var userName = userDetails.userName();
        final var userPassword = userDetails.userPassword();
        return nonNull(userName) && !userName.isEmpty() && nonNull(userPassword) && !userPassword.isEmpty();
    }

}
