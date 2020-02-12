package com.chatbot.admin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotButton;
import com.chatbot.admin.entities.BotButtonTemplateMSG;
import com.chatbot.admin.entities.BotQuickReplyMessage;
import com.chatbot.admin.entities.BotTemplateElement;
import com.chatbot.admin.entities.ButtonType;

public interface BotButtonRepo extends JpaRepository<BotButton, Long> {
				
	public List<BotButton> findAllByIsDeletedFalseAndButtonType(ButtonType type);
	public List<BotButton> findAllByIsDeletedFalseAndBotQuickReplyMessage(BotQuickReplyMessage quickReply);
	public List<BotButton> findAllByIsDeletedFalseAndBotTemplateElement(BotTemplateElement element);
	public List<BotButton> findAllByIsDeletedFalseAndBotButtonTemplateMSG(BotButtonTemplateMSG buttonTemplate);
	public Optional<BotButton> findByIsDeletedFalseAndButtonId(Long buttonId);
	public List<BotButton> findAllByIsDeletedFalse();
	 
}
