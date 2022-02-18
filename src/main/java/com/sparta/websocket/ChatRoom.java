package com.sparta.websocket;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

// stomp - pub/sub 방식으로 웹소켓 세션 관리가 필요없어짐. DTO간소화 작업
//웹소켓 핸들러 필요없어짐 - 주석처리함.

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}



//@Getter
//public class ChatRoom {
//    private String roomId;
//    private String name;
////    채팅방에 입장한 클라이언트들의 정보를 가지고 있어야하므로 WebsocketSession 정보 리스트를 멤버필드로 갖는다.
//    private Set<WebSocketSession> sessions = new HashSet<>();
//
//    @Builder
//    public ChatRoom(String roomId, String name) {
//        this.roomId = roomId;
//        this.name = name;
//    }
//
//
////  채팅방에서 입장, 대화하기의 기능이 있으므로 handleAction을 통해 분기처리
//    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
//
////        채팅방 입장시에 채팅룸의 session정보에 클라이언트의 session리스트를 추가해 놓고 채팅룸에 메세지가 도착할 경우
////        채팅룸 의 모든 session에 메세지를 발송하면 채팅완성
//        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
//            sessions.add(session);
//            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
//        }
//        sendMessage(chatMessage, chatService);
//    }
//
//    public <T> void sendMessage(T message, ChatService chatService) {
//        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
//    }
//}
