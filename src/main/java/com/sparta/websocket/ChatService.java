package com.sparta.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;


//채팅방을 생성조회, 하나의 세션에 메세지를 발송하는 서비스
//

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;

//    채팅방 맵은 서버에 생성된 모든 채팅방의 정보를 모아둔 구조체 .
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
//    채팅룸의 정보저장은 빠른구현을 위해 외부저장소를 이용하지 않고 HashMAp에 저장하는 것으로 구현.
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }
// 채팅방 조회 : 채팅방 MAp에 담긴 정보를 조회 .
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }
//  채팅방 생성 - 랜덤 uuid 값으로 구별 id를 가진 채팅방 객체 생성하고 , 채팅방 map에 추가.
    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }
//   메세지 발송 : 지정한 websocket세션에 메세지 발송,
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}