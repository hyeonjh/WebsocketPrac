package com.sparta.websocket.chat;

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

//        단순 메시지 브로커를 활성화하고 하나 이상의 접두사를 구성하여 브로커를 대상으로 하는 대상을 필터링합니다(예: 접두사가 "/topic"인 대상).
        config.enableSimpleBroker("/sub");

        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
//    각각을 특정 URL에 매핑하고 SockJS 폴백 옵션을 활성화 및 구성(선택 사항)하는 STOMP 끝점을 등록합니다.
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        stomp websocket의 연결 endpoint 설정
//        개발서버의 접속주소 - ws://localhost:8080/ws-stomp 처럼 됨
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("http://localhost:8080/")
                .withSockJS();
    }
}


//    메세지 브로커를 통해서 implements를 가져옴
