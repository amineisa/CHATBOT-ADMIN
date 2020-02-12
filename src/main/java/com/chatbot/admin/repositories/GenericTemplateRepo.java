package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotGTemplateMessage;
import com.chatbot.admin.entities.InteractionMessage;

public interface GenericTemplateRepo extends JpaRepository<BotGTemplateMessage, Long> {

	public BotGTemplateMessage findByIsDeletedFalseAndTemplateId(Long templateID);

	public BotGTemplateMessage findByIsDeletedFalseAndInteractionMessage(InteractionMessage message);

	public List<BotGTemplateMessage> findAllByIsDeletedFalse();
}
