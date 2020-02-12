package com.chatbot.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.WebServiceResponseType;

public interface WebServiceResponseTypeRepo extends  JpaRepository<WebServiceResponseType, Long> {

	public List<WebServiceResponseType> findAllByisDeletedFalse();
	public WebServiceResponseType findByIsDeletedFalseAndResponseTypeId(Long id);
}
