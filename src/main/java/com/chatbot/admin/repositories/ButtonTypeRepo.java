package com.chatbot.admin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.ButtonType;

public interface ButtonTypeRepo extends JpaRepository<ButtonType, Long> {
	
	List<ButtonType> findAllByIsDeletedFalse();
	Optional<ButtonType> findByIsDeletedFalseAndId(Long id);
	boolean existsByButtonName(String buttonName);
	boolean existsByIsDeletedFalseAndId(Long id);
	
}
