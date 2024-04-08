package com.boot.security.websocketss;

import org.springframework.stereotype.Component;


@Component
public class ChatMessage {

	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	private MessageType type;
	private String content;
	private String sender;
	private String profilephoto;

	public ChatMessage(MessageType type, String content, String sender, String profilephoto) {
		super();
		this.type = type;
		this.content = content;
		this.sender = sender;
		this.profilephoto = profilephoto;
	}
	
	public String getProfilephoto() {
		return profilephoto;
	}
	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}