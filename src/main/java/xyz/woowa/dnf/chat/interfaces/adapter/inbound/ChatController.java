package xyz.woowa.dnf.chat.interfaces.adapter.inbound;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/chat")
    public String chatRoom() {
        return "chat/chat-room";
    }
}
