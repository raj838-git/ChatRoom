package com.boot.security.websocketss;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventLister {
	
	
//	private SimpMessageSendingOperations messageSendingOperations;
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ChatMessage chatMessage;
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if(username != null) {
			logger.info("Disconneted User {}", username);
			chatMessage.setType(MessageType.LEAVE);
			chatMessage.setSender(username);
			chatMessage.setContent("Left");
			chatMessage.setProfilephoto(null);
			
			simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
			
			
		}
		
	}
}