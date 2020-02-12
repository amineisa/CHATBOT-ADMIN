package com.chatbot.admin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatbot.admin.entities.EnabledCategoryConfiguration;

public interface EnabledCategoryRepo extends JpaRepository<EnabledCategoryConfiguration,Long>{

	public List<EnabledCategoryConfiguration> findAllByIsDeletedFalse();
	public Optional<EnabledCategoryConfiguration> findByIsDeletedFalseAndId(Long id);
	public boolean existsByIsDeletedFalseAndId(Long id);
}
