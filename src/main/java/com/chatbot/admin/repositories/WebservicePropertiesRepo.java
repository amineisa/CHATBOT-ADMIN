package com.chatbot.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.entities.WebserviceProperties;

public interface WebservicePropertiesRepo extends JpaRepository<WebserviceProperties, Long>{

	public WebserviceProperties findAllByInteractionMessage(InteractionMessage msg);
	
}
