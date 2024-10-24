package pl.polsl.rest.api.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import pl.polsl.rest.api.service.HttpSessionService;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
public class HttpSessionInterceptor implements HandshakeInterceptor {

    private final HttpSessionService sessionService;

    public HttpSessionInterceptor(final HttpSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Map<String, Object> attributes) {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpSession session = servletRequest.getServletRequest().getSession(false);
        return !isNull(session) && sessionService.isLoggedIn(session);
    }

    @Override
    public void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler,
                               final Exception exception) {
    }

}
