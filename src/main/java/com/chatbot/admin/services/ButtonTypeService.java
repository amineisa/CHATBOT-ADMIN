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

import com.chatbot.admin.entities.ButtonType;
import com.chatbot.admin.exceptions.BadRequestHandler;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.ButtonTypeRepo;

@Service
public class ButtonTypeService {

	@Autowired
	private ButtonTypeRepo buttonTypeRepo;

	private static final Logger logger = LoggerFactory.getLogger(ButtonTypeService.class);

	// Retrieve All Button Types
	public ResponseEntity<List<ButtonType>> getAlltypes() {
		List<ButtonType> body = buttonTypeRepo.findAllByIsDeletedFalse();
		logger.debug("Retrieve all button types ");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	// Retrieve specific ButtonType by its id
	public ResponseEntity<ButtonType> getButtonTypeById(Long id) {
			Optional<ButtonType> optionalType = buttonTypeRepo.findByIsDeletedFalseAndId(id);
			if (optionalType.isPresent()) {
				logger.debug("Retrieve type object  : " + optionalType.get().toString());
				return new ResponseEntity<>(optionalType.get(), HttpStatus.OK);
			} else {
				logger.debug("you try to retrieve an unexisting type");
				throw new NotFoundHandler("you try to retrieve an unexisting type");
			}
		} 
	

	// Insert new Type
	public ResponseEntity<ButtonType> insertNewType(ButtonType type) {
		if (!type.checkEmpty()) {
			if(!buttonTypeRepo.existsByButtonName(type.getButtonName())) {
			type.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			type.setIsDeleted(false);
			logger.debug("Insert new type " + type.toString());
			return new ResponseEntity<>(buttonTypeRepo.save(type), HttpStatus.CREATED);
			}else {
				logger.debug("Unique constraints violated type name ");
				throw new BadRequestHandler("Check unique value Button type name");
			}
		} else {
			logger.debug("you can not insert null values");
			throw new UnprocessablEHandler("Check required values");
		}
	}

	// update an existing type
	public ResponseEntity<ButtonType> updateExistingType(ButtonType type) {
		if (type.checkEmpty()) {
			Optional<ButtonType> optionalType = buttonTypeRepo.findByIsDeletedFalseAndId(type.getId());
			if (optionalType.isPresent()) {
				ButtonType existingtype = optionalType.get();
				logger.debug("Updating button type "+type.toString());
				existingtype.setId(type.getId());
				existingtype.setButtonName(type.getButtonName());
				return new ResponseEntity<>(existingtype, HttpStatus.ACCEPTED);
			} else {
				logger.debug("NO Button type found "+type.getId());
				throw new NotFoundHandler("NO Button type found "+type.getId());
			}
		} else {
			logger.debug("Invalid Value for updating button type Null Value");
			throw new UnprocessablEHandler("Invalid Value for updating button type");
		}
	}

	// delete an existing type
	public ResponseEntity<Void> deleteExistingType(Long typeId) {
		Optional<ButtonType> optionalType = buttonTypeRepo.findByIsDeletedFalseAndId(typeId);
		if (optionalType.isPresent()) {
			ButtonType type = optionalType.get();
			logger.debug("Deleting type object which its id is " + typeId);
			type.setIsDeleted(true);
			type.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			buttonTypeRepo.save(type);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.debug("You try to delete an unexisting object");
			throw new NotFoundHandler("You try to delete an unexisting button type");
		}

	}

}
