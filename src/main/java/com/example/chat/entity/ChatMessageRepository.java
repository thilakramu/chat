package com.example.chat.entity;

import org.springframework.data.repository.CrudRepository;
import com.example.chat.entity.ChatMessage;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {

}
