package bk.tailfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileContentRecordingService {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	public void sendLinesToTopic(String line) {
		this.simpMessagingTemplate.convertAndSend("/topic/tailfiles", line);
	}
}
