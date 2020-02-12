package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotConfiguration;

public interface ConfigurationRepo extends JpaRepository<BotConfiguration, Long> {
	
	public List<BotConfiguration> findAllByIsDeletedFalse();
	public BotConfiguration findByIsDeletedFalseAndId(Long id);

}
