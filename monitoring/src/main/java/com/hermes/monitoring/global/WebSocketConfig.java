
package com.hermes.monitoring.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //sockJs 클라이언트가 Websocket 핸드셰이크를 하기 위해 연결할 endpoint를 지정할 수 있다.
        // registry.addEndpoint("/ws/chat", "/live-auction")
        registry.addEndpoint("/ws/monitoring")
                .setAllowedOriginPatterns("*");
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/chat" , "/participants");
        registry.enableSimpleBroker("/sub");
//        registry.setUserDestinationPrefix("/pub");
        registry.setApplicationDestinationPrefixes("/pub"); // mapping 클래스 명시

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //log.info("연결됨!!!");
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
    }
}
