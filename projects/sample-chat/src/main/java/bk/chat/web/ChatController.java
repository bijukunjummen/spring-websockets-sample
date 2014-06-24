package bk.chat.web;

import bk.chat.model.ChatMessage;
import com.google.common.collect.EvictingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chats")
	public void handleChat(@Payload ChatMessage message, Principal user) {
		this.simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/chats", "[" + getTimestamp() + "]:" + user.getName() + ":" + message.getMessage());
		this.simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/queue/chats", "[" + getTimestamp() + "]:" + user.getName() + ":" + message.getMessage());
	}

	private String getTimestamp() {
		LocalDateTime date = LocalDateTime.now();
		return date.format(DateTimeFormatter.ISO_DATE_TIME);
	}
}
