package pl.polsl.rest.api.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pl.polsl.rest.api.service.WebsocketService;
import pl.polsl.rest.api.utils.WebSocketSessionManager;

@Component
public class WebsocketHandler extends TextWebSocketHandler {
    private static final String USER_ID_ATTRIBUTE = "USER_ID";
    private final WebSocketSessionManager sessionManager;
    private final WebsocketService websocketService;

    public WebsocketHandler(final WebSocketSessionManager sessionManager,
                            final WebsocketService websocketService) {
        this.sessionManager = sessionManager;
        this.websocketService = websocketService;
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        try {
            super.afterConnectionEstablished(session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.sessionManager.addSession(this.getUserId(session), session);
        this.websocketService.sendNewUserLoggedNotify();
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        try {
            super.afterConnectionClosed(session, status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.sessionManager.removeSession(this.getUserId(session));
        this.websocketService.sendUserLogoutNotify();
    }

    private Integer getUserId(final WebSocketSession webSocketSession) {
        return (Integer) webSocketSession.getAttributes().get(USER_ID_ATTRIBUTE);
    }

}
