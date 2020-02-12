package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotGTemplateMessage;
import com.chatbot.admin.entities.BotTemplateElement;


public interface BotElementRepo extends JpaRepository<BotTemplateElement, Long>  {
	
	public List<BotTemplateElement> findAllByIsDeletedFalseAndBotGTemplateMessage(BotGTemplateMessage gTemplateMessage);
	public BotTemplateElement findByIsDeletedFalseAndElementId(Long id);
	public List<BotTemplateElement> findAllByIsDeletedFalse();
	
	
	

}
