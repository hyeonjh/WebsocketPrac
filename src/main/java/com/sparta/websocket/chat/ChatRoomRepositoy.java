package com.sparta.websocket.chat;

import com.sparta.websocket.chat.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

//서비스를 repository가 대체하도록함.
@Repository
public class ChatRoomRepositoy {
//Map은 리스트나 배열처럼 순차적으로(sequential) 해당 요소 값을 구하지 않고 key를 통해 value를 얻는다.
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
//    입력된 순서대로 Key가 보장 - LinkedHashMap generation
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
//        오름차순
        // 채팅방 생성순서 최근 순으로 반환
//        values() 메소드는 해당 열거체의 모든 상수를 저장한 배열을 생성하여 반환합니다.
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);// 채팅방 생성순서 최근 순으로 반환
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}



