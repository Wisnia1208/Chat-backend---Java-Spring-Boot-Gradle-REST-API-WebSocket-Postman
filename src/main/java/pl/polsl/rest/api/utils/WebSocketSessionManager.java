package pl.polsl.rest.api.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final static ConcurrentHashMap<Integer, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();

    public void addSession(final int userId, final WebSocketSession session) {
        SESSION_MAP.put(userId, session);
    }

    public WebSocketSession getSession(final int userId) {
        return SESSION_MAP.get(userId);
    }

    public Collection<WebSocketSession> getAllSessions() {
        return SESSION_MAP.values();
    }

    public void removeSession(final int userId) {
        SESSION_MAP.remove(userId);
    }

    public boolean isUserOnline(final int userId) {
        return SESSION_MAP.containsKey(userId);
    }
}
