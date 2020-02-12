package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotQuickReplyMessage;
import com.chatbot.admin.entities.InteractionMessage;

public interface QuickReplyTemplateRepo extends JpaRepository<BotQuickReplyMessage, Long> {
	
	public BotQuickReplyMessage findByIsDeletedFalseAndQuickMsgId(Long tempId);
	public List<BotQuickReplyMessage> findAllByIsDeletedFalse();
	public BotQuickReplyMessage findBotByIsDeletedFalseAndInteractionMessage(InteractionMessage msg);
	
	
	
}
