package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotMessageType;

public interface MessageTypeRepo extends JpaRepository<BotMessageType, Long> {

	public List<BotMessageType> findAllByIsDeletedFalse();
	public BotMessageType findByIsDeletedFalseAndMessageTypeId(Long id);
}
