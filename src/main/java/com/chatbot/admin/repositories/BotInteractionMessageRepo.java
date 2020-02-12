package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotInteraction;
import com.chatbot.admin.entities.InteractionMessage;

public interface BotInteractionMessageRepo extends JpaRepository<InteractionMessage, Long>{
	
	//retrieve messages which assigned to specific interaction
	public List<InteractionMessage> findBotInteractionMessagesByIsDeletedFalseAndBotInteraction(BotInteraction interaction); 
	public List<InteractionMessage> findAllBotInteractionMessagesByisDeletedFalse();
	public InteractionMessage findByIsDeletedFalseAndMessageId(Long messageId);
	
}
