package com.example.chat.data;

import com.example.chat.entity.ChatMessage;

public class ChatData {

	private ChatMessage message;

	public ChatData(ChatMessage message) {
		super();
		this.message = message;
	}

	public ChatMessage getMessage() {
		return message;
	}

	public void setMessage(ChatMessage message) {
		this.message = message;
	}

}

