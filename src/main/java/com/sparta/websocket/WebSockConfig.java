package com.sparta.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker  // stomp 사용하기위해 선언
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
//    WebSocketMessageBrokerConfigurer 상속 받아서 configureMessageBroker 구현
    public void configureMessageBroker(MessageBrokerRegistry config) {

//        pub/sub 메시징 구현하기 위한 로직.
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        stomp websocket의 연결 endpoint 설정
//        개발서버의 접속주소 - ws://localhost:8080/ws-stomp 처럼 됨
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("http://localhost:8080/")
                .withSockJS();
    }
}
