package pl.polsl.rest.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.polsl.rest.api.dto.*;
import pl.polsl.rest.api.entity.User;
import pl.polsl.rest.api.repository.UserRepository;
import pl.polsl.rest.api.utils.WebSocketSessionManager;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    private final HttpSessionService sessionService;
    private final WebsocketService websocketService;

    private final WebSocketSessionManager wsSessionManager;

    public UserService(final UserRepository repository, final WebSocketSessionManager wsSessionManager, final WebsocketService websocketService,  final HttpSessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.wsSessionManager = wsSessionManager;
        this.websocketService = websocketService;
    }

    public Optional<UserDataDto> register(final AuthDto dto) {
        final Optional<User> users = this.repository.findUserByName(dto.userName());
        if (users.isPresent()) {
            return Optional.empty();
        }
        User entity = this.repository.save(this.mapAuthDtoToEntity(dto));

        if (entity.getId() != 0) {
            return Optional.of(this.mapToUserDataDto(entity));
        }
        return Optional.empty();
    }

    //TODO: 1
    public Optional<UserDataDto> login(final AuthDto authDto, final HttpSession session)
    {
        final Optional<User> user = this.repository.findUserByNameAndPassword(authDto.userName(),authDto.userPassword());

        user.ifPresent(value -> sessionService.bindSessionToUser(session, value.getId(), authDto.userName()));
        if(user.isPresent())
        {
            websocketService.sendNewUserLoggedNotify();
            return Optional.of(new UserDataDto(user.get().getId(), authDto.userName()));
        }

        return Optional.empty();
    }

    // TODO: 3
    public void logout(final HttpSession session) {
        if(sessionService.isLoggedIn(session))
        {
            sessionService.invalidateSession(session);
            websocketService.sendUserLogoutNotify();
        }
    }

    //TODO: 5
    public DataDto<OnlineUserDataDto> getUsers() {
        final List<OnlineUserDataDto> dto = new ArrayList<>();

        List<User> users = this.repository.findAll();

        for (User user : users) {

            dto.add(new OnlineUserDataDto(user.getId(), user.getName(), wsSessionManager.isUserOnline(user.getId())));
        }

        return new DataDto<OnlineUserDataDto>(dto);
    }

    private User mapAuthDtoToEntity(final AuthDto dto) {
        final var entity = new User();
        entity.setName(dto.userName());
        entity.setPassword(dto.userPassword());
        return entity;
    }

    private UserDataDto mapToUserDataDto(final User entity) {
        return new UserDataDto(entity.getId(), entity.getName());
    }

}
