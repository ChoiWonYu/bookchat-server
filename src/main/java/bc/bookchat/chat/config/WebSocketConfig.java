package bc.bookchat.chat.config;

import bc.bookchat.chat.auth.StompHandshakeHandler;
import bc.bookchat.chat.auth.StompInterceptHandler;
import bc.bookchat.chat.exception.StompErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompErrorHandler stompErrorHandler;
    private final StompHandshakeHandler stompHandshakeHandler;
    private final StompInterceptHandler stompInterceptHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub", "/user");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .setErrorHandler(stompErrorHandler)
            .addEndpoint("/websocket-stomp")
            .setAllowedOriginPatterns("*")
            .setHandshakeHandler(stompHandshakeHandler)
            .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(stompInterceptHandler);
    }
}
