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

import com.chatbot.admin.entities.PersistenceMenuButton;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.ButtonTypeRepo;
import com.chatbot.admin.repositories.PersistenceMenuRepo;

@Service
public class PersistenceMenuService {

	@Autowired
	private PersistenceMenuRepo persistenceMenuRepo;
	@Autowired
	private ButtonTypeRepo buttonTypeRepo;
	
	
	private static final Logger logger = LoggerFactory.getLogger(PersistenceMenuService.class);
	
	//All Persistence Menu Buttons
	public ResponseEntity<List<PersistenceMenuButton>> getAllButtons(){
		List<PersistenceMenuButton> body = persistenceMenuRepo.findAllByisDeletedFalse();
		logger.debug("Retrieve all persistence menu's buttons ");
		return new ResponseEntity<>(body , HttpStatus.OK);
	}
	 
	// specific button by id 
	public ResponseEntity<PersistenceMenuButton> getOneButtonById(Long id ){
		Optional<PersistenceMenuButton> optionalButton = persistenceMenuRepo.findByIsDeletedFalseAndId(id);
		if(optionalButton.isPresent()) {
			PersistenceMenuButton button = optionalButton.get();
		logger.debug("Retrieve persistence menu button by id "+id);	
		return new ResponseEntity<>(button, HttpStatus.OK);
		}else {
			logger.debug("No persistence button found by this id "+id);
			throw new NotFoundHandler("No persistence button found by this id "+id);
		}
	}
	
	//List of buttons by parent id
	public ResponseEntity<List<PersistenceMenuButton>> getAllByParentId(Long id){
		boolean isExist = persistenceMenuRepo.existsByIsDeletedFalseAndIsNestedTrueAndId(id);
		if(isExist) {
			logger.debug("Return persistence menu button list which included under parent button "+id);
			List<PersistenceMenuButton> buttons = persistenceMenuRepo.findAllByIsDeletedFalseAndParentId(id);
			return new ResponseEntity<>(buttons,HttpStatus.OK);
		}else {
			logger.warn("No parent button found by this id "+id);
			throw new NotFoundHandler("No parent button found by this id "+id);
		}
	}
	
	//Insert new Button 
	public ResponseEntity<PersistenceMenuButton> insertNewButton(PersistenceMenuButton button){
		if(button.getParentId() != 0 && button.getButton().checkEmpty()) {
			if(persistenceMenuRepo.existsByIsDeletedFalseAndIsNestedTrueAndId(button.getParentId()) && buttonTypeRepo.existsByIsDeletedFalseAndId(button.getButton().getButtonType().getId())) {
				return saveButton(button);
			}else {
				throw new NotFoundHandler("No button exist by parent id or this button is not nested ");
			}	
		}else if(button.getParentId() == 0 && button.getButton().checkEmpty() && buttonTypeRepo.existsByIsDeletedFalseAndId(button.getButton().getButtonType().getId())){			
			return saveButton(button);
		}else {
			logger.error("Invalid Data for saving persistence menu button ");
			throw new UnprocessablEHandler("Invalid Data for persistence menu button check required values");
		}
	}

	private ResponseEntity<PersistenceMenuButton> saveButton(PersistenceMenuButton button) {
		logger.debug("Insert new persistence menu button "+button);
		button.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(persistenceMenuRepo.save(button), HttpStatus.CREATED);
	}
	
	//Update existing button
	public ResponseEntity<PersistenceMenuButton> updateExistingButton(PersistenceMenuButton button){
		if(button != null && button.getId() != null) {
			Optional<PersistenceMenuButton> optionalButton = persistenceMenuRepo.findByIsDeletedFalseAndId(button.getId());
			if(optionalButton.isPresent()/*&& refrenceButton != null*/) {
				PersistenceMenuButton existButton = optionalButton.get();
				logger.debug("Update Existing persistence menu button "+button.toString());
				existButton.setIsNested(button.getIsNested());
				existButton.setParentId(button.getParentId());
				existButton.setPriority(button.getPriority());
				existButton.setButton(button.getButton());
				persistenceMenuRepo.save(existButton);
				return new ResponseEntity<>(existButton, HttpStatus.ACCEPTED);
			}else {
				logger.warn("No button found for updating ");
				throw new NotFoundHandler("No button found for updating ");
			}
		}else {
			logger.error("Invalid Data for persistence menu button ");
			throw new UnprocessablEHandler("Invalid Data for persistence menu button ");
		}
	}
	
	
	//Delete existing button 
	public ResponseEntity<Void> deleteExistButton(Long id ){
		Optional<PersistenceMenuButton> optionalButton = persistenceMenuRepo.findByIsDeletedFalseAndId(id);
		if(optionalButton.isPresent()) {
			PersistenceMenuButton button = optionalButton.get();
			logger.debug("Delete persistence menu button "+id);
			button.setIsDeleted(true);
			button.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			if(button.getIsNested()) {
				persistenceMenuRepo.findAllByIsDeletedFalseAndParentId(button.getId()).forEach(perButton ->{
					button.setIsDeleted(true);
					button.setDeletedDate(new Timestamp(System.currentTimeMillis()));
					persistenceMenuRepo.save(perButton);
				});
			}
			persistenceMenuRepo.save(button);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			logger.debug("No Persistence menu button found for deleting "+id);
			throw new NotFoundHandler("No Persistence menu button found for deleting "+id);
		}
	}
}
