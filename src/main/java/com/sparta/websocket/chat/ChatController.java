package com.sparta.websocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


// rest - > controller 로 수정 (publisher 구현)

// publisher 구현
// 서버단에서 따로 추가할 구현이 없다.
// 웹뷰에서  stomp라이브러리를 이용해 sub주소를 바라보고 있는 코드만 작성하면됨


@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

//  websocket으로 들어오는 메세지 발행을 처리.
//  클라이언트에서 prefix 를 붙여서 /pub/chat/message로 발행요청하면 controller가 메세지를 받아서 처리.
//  메세지가 발행되면 /sub/chat/room/{roomId}로 메세지를 send
//    클라이언트에서 (/sub/chat/room/{roomId})주소를 구독(sub)하고 있다가 메시지가 전달되면 화면에 출력.
//    /sub/chat/room/{roomId}는 채팅룸을 구분하는 값이므로 pub/sub에서 topic의 역할.
//    기존의 websockcharhandler가 햇던 역할 대체 .



//    이 주석으로 표현되는 목적지 기반 매핑.
//WebSocket을 통한 STOMP 메시지의 경우 이것은 메시지의 STOMP 대상과 일치하는 AntPathMatcher 스타일 패턴입니다.
//RSocket의 경우 이것은 AntPathMatcher 또는 PathPattern 기반 패턴이며 구성에 따라 스트림 요청의 경로와 일치합니다.
//패턴이 구성되지 않은 경우 매핑은 모든 대상과 일치합니다.
    @MessageMapping("/chat/message") //메세지가 이위치로 날아감
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        링크주소로 , 페이로드로 사용할 객체 , 보낼 메시지의 헤더값
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}










//@RequiredArgsConstructor
//@RestController
////생성 조회 Rett api로 구현.
//@RequestMapping("/chat")
//public class ChatController {
//
//    private final ChatService chatService;
//
//    @PostMapping
//    public ChatRoom createRoom(@RequestParam String name) {
//        return chatService.createRoom(name);
//    }
//
//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }
//}

