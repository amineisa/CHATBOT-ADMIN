package com.chatbot.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotTextResponseMapping;

public interface TextResponseMappingRepo extends JpaRepository<BotTextResponseMapping, Long> {

}
