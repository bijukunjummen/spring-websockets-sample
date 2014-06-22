package bk.tailfile.web;

import com.google.common.collect.EvictingQueue;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
public class GreetingsController {

	private EvictingQueue<String> oldMessages = EvictingQueue.create(20);

	@MessageMapping("/greetings")
	@SendTo("/topic/greetings")
	public String handleGreeting(@Payload String greeting) {
		String message = "[" + getTimestamp() + "]:" + greeting;
		oldMessages.add(message);
		return message;
	}

	@SubscribeMapping("/oldmessages")
	public List<String> oldMessages() {
		return Arrays.asList(oldMessages.toArray(new String[0]));
	}

	private String getTimestamp() {
		LocalDateTime date = LocalDateTime.now();
		return date.format(DateTimeFormatter.ISO_DATE_TIME);
	}
}
