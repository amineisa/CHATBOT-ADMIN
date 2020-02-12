package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotButtonTemplateMSG;
import com.chatbot.admin.entities.InteractionMessage;

public interface ButtonTemplateRepo extends JpaRepository<BotButtonTemplateMSG, Long> {

	public BotButtonTemplateMSG findBotButtonTemplateMSGByIsDeletedFalseAndInteractionMessage(InteractionMessage message);
	public List<BotButtonTemplateMSG> findAllBotButtonTemplateMSGByIsDeletedFalse();
	public BotButtonTemplateMSG findByIsDeletedFalseAndButtonTempMsgId(Long templateId);
}
