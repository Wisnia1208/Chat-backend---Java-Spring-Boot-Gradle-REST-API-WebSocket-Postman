package pl.polsl.rest.api.service;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import pl.polsl.rest.api.constants.WebsocketStatuses;
import pl.polsl.rest.api.utils.WebSocketSessionManager;

import java.io.IOException;

@Service
public class WebsocketService {

    private final WebSocketSessionManager wsSessionManager;

    public WebsocketService(final WebSocketSessionManager wsSessionManager) {
        this.wsSessionManager = wsSessionManager;
    }

    @SneakyThrows
    public void sendStatusMessageToOnlineUsers(int status) {
        final var sessions = this.wsSessionManager.getAllSessions();
        for (var session : sessions) {
            try {
                var response = new JSONObject();
                response.put("status", status);
                session.sendMessage(new TextMessage(response.toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendStatusMessageToUser(int status, int userId) {
        final var session = this.wsSessionManager.getSession(userId);
        if (session != null) {
            var response = new JSONObject();
            response.put("status", status);
            try {
                session.sendMessage(new TextMessage(response.toString()));
            } catch (IOException ignored) {
            }
        }
    }

    public void closeConnectionWithUser(int userId) {
        final var session = this.wsSessionManager.getSession(userId);
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SneakyThrows
    public void sendMessageSentNotify(int userId) {
        sendStatusMessageToUser(WebsocketStatuses.MESSAGE_SENT, userId);
    }

    @SneakyThrows
    public void sendNewMessageNotify(int userId) {
        sendStatusMessageToUser(WebsocketStatuses.MESSAGE_NEW, userId);
    }

    @SneakyThrows
    public void sendNewUserLoggedNotify() {
        sendStatusMessageToOnlineUsers(WebsocketStatuses.USER_LOGIN);
    }

    @SneakyThrows
    public void sendUserLogoutNotify() {
        sendStatusMessageToOnlineUsers(WebsocketStatuses.USER_LOGOUT);
    }

}
