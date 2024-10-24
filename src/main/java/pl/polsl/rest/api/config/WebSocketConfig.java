package pl.polsl.rest.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import pl.polsl.rest.api.handler.HttpSessionInterceptor;
import pl.polsl.rest.api.handler.WebsocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {

	private static final String WEBSOCKET_ENDPOINT = "/websocket";
	private final WebsocketHandler msgHandler;
	private final HttpSessionInterceptor customHandshakeHandler;

	public WebSocketConfig(final WebsocketHandler msgHandler, final HttpSessionInterceptor customHandshakeHandler) {
		this.msgHandler = msgHandler;
		this.customHandshakeHandler = customHandshakeHandler;
	}

	@Override
	public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
		registry.addHandler(this.msgHandler, WEBSOCKET_ENDPOINT).addInterceptors(new HttpSessionHandshakeInterceptor(), customHandshakeHandler).setAllowedOrigins("*");
	}
}


