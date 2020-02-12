package com.chatbot.admin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.PersistenceMenuButton;

public interface PersistenceMenuRepo extends JpaRepository<PersistenceMenuButton, Long> {

	public Optional<PersistenceMenuButton> findByIsDeletedFalseAndId(Long id);
	public List<PersistenceMenuButton> findAllByIsDeletedFalseAndParentId(Long id);
	public List<PersistenceMenuButton> findAllByisDeletedFalse();
	public boolean existsByIsDeletedFalseAndIsNestedTrueAndId(Long id);
	
}
