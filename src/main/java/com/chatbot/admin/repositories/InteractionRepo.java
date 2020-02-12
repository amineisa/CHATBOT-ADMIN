package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotInteraction;

public interface InteractionRepo extends JpaRepository<BotInteraction, Long> {

	public List<BotInteraction> findAllByisDeletedFalse();
	public BotInteraction findByIsDeletedFalseAndInteractionId(Long interactionId);
}
