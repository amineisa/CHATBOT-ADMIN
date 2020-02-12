package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotTextMessage;
import com.chatbot.admin.entities.InteractionMessage;

public interface TextMessageRepo extends JpaRepository<BotTextMessage, Long>{

	List<BotTextMessage> findAllByIsDeletedFalse();
	BotTextMessage findByIsDeletedFalseAndTextMsgId(Long textMsgId);
	BotTextMessage findByIsDeletedFalseAndInteractionMessage(InteractionMessage message);
	
}
