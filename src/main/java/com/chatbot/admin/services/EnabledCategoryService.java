package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.EnabledCategoryConfiguration;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.EnabledCategoryRepo;

@Service
public class EnabledCategoryService {

	@Autowired
	EnabledCategoryRepo enabledCategoryRepo;

	private static final Logger logger = LoggerFactory.getLogger(EnabledCategoryService.class);

	// Retrieve All enabled categories
	public ResponseEntity<List<EnabledCategoryConfiguration>> allEnabledCategories() {
		List<EnabledCategoryConfiguration> body = enabledCategoryRepo.findAllByIsDeletedFalse();
		logger.debug("Retrieve All enabled categories ");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	// Retrieve specific category
	public ResponseEntity<EnabledCategoryConfiguration> specificCategoryById(Long id) {
		Optional<EnabledCategoryConfiguration> optionalCategory = enabledCategoryRepo.findByIsDeletedFalseAndId(id);
		if (optionalCategory.isPresent()) {
			logger.debug("Retrieve Category " + optionalCategory.get().toString());
			return new ResponseEntity<>(optionalCategory.get(), HttpStatus.OK);
		} else {
			logger.debug("No category found by id " + id);
			throw new NotFoundHandler("No category found by id " + id);
		}
	}
	
	//Insert new enabled category object 
	public ResponseEntity<EnabledCategoryConfiguration> insertNewObject(EnabledCategoryConfiguration category){
		if(category.checkEmpty()) {
			logger.debug("insert new Enabled category "+category.toString());
			category.setIsDeleted(false);
			category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(enabledCategoryRepo.save(category),HttpStatus.CREATED);
		}else {
			logger.error("Invalid date for insertion category Null Values ");
			throw new UnprocessablEHandler("Invalid date for insertion category check required values ");
		}
	}
	
	//Update existing Category
	public ResponseEntity<EnabledCategoryConfiguration> updateCategory(EnabledCategoryConfiguration category){
		if(category.getId() != null) {
			Optional<EnabledCategoryConfiguration> optionalCategory = enabledCategoryRepo.findByIsDeletedFalseAndId(category.getId());
			if(optionalCategory.isPresent()) {
				EnabledCategoryConfiguration existategory = optionalCategory.get();
				logger.debug("update  Enabled category "+existategory.toString());
				existategory.setArabicCategories(category.getArabicCategories());
				existategory.setEnglishCategories(category.getEnglishCategories());
				return new ResponseEntity<>(enabledCategoryRepo.save(existategory),HttpStatus.ACCEPTED);
			}else {
				logger.debug("NO category found by id "+category.getId());
				throw new NotFoundHandler("NO category found to be updated ");
			}
			
		}else {
			logger.error("Invalid values no category id found check your request body");
			throw new UnprocessablEHandler("Invalid date for updating category Null Values ");
		}
	}
	
	public ResponseEntity<Void> deleteCategory(Long id ){
		Optional<EnabledCategoryConfiguration> optionalCategory = enabledCategoryRepo.findByIsDeletedFalseAndId(id);
		if(optionalCategory.isPresent()) {
			EnabledCategoryConfiguration existingCategory = optionalCategory.get();
			logger.debug("Deleting Category by id "+id );
			existingCategory.setIsDeleted(true);
			existingCategory.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			enabledCategoryRepo.save(existingCategory);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			logger.warn("Category Not found for deleting " +id );
			throw new NotFoundHandler("Not found category by this " +id );
			
		}
	}
}
