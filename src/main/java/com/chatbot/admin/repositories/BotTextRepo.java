package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotText;

public interface BotTextRepo extends JpaRepository<BotText, Long> {

	List<BotText> findAllByIsDeletedFalse();
	BotText findByIsDeletedFalseAndTextId(Long textId);
	
}
