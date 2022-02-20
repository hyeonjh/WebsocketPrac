package com.sparta.websocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//template/chat/room.ftl 파일과 /chat/roomdetail.ftl 파일생성
//채팅방리스트 생성 , 채팅방 상세화면을 위한view
//ftl 은 freemarker형식 껍데기 , 내부는 vue.js , 기본 uo bootstrap

//  template/chat/room.ftl 채팅방을 개설하거나 현재 채팅방의 리스트를 보여줌, 리스트 클릭시 채팅방 입장.

//  template/chat/roomdetail.ftl  -- 채팅방에 입장했을떄 view , 메시지 주고받을 수 있음.
// ws-stomp로 서버연결을 한 후 , 채팅룸에 구독하느 액션 수행가능

//ws.subscribe("/sub/chat/room/" + vm.$data.roomId, function(message){ ...}
//구독은 '/sub/chat/room/채팅방번호' 로 구독 , 이주소를 topic으로 삼아서 서버에서 메시지 발행.
// 채팅방에서는 클라이언트가 메시지를 입려갛면 서버에서 topic(/sub/chat/room/채팅방번호)로 메시지 발행,
// 이것을 ws.subscribe에서 대기하다가 발송된 메시지를 받아서 처리 .



@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepositoy chatRoomRepository;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

}
