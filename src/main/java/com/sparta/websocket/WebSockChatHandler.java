package com.sparta.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// import 생략....

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
// 삭제        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^ ");
// 삭제       session.sendMessage(textMessage);

//        웹소켓 클라이언트로 부터 채팅메세지를 전달받아서 채팅메시지 객체로 변환 .
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//        전달받은 메세지에 담긴 채팅방 id로 발송 대상 채팅방 정보를 조회함.
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
//        해당 채팅방에 입장해 있는 모든 클라이언트 들 (웹소켓 세션 ) 에게 타입에 따른 메세지 발송 .
        room.handleActions(session, chatMessage, chatService);
    }
}