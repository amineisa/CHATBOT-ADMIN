package com.chatbot.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.BotMethodType;

public interface MethodTypeRepo extends JpaRepository<BotMethodType, Long> {

}
