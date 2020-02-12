package com.chatbot.admin.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotMethodType;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.MethodTypeRepo;

@Service
public class MethodTypeService {

	@Autowired
	MethodTypeRepo typeRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(MethodTypeRepo.class);
	
	//Retrieve All Method type
	public ResponseEntity<List<BotMethodType>> getAllMethodTypes(){
		return new ResponseEntity<>(typeRepo.findAll(), HttpStatus.OK);
	}
	
	//Retrieve specific Method type
	public ResponseEntity<BotMethodType> getMethodTypeById(Long id){
		Optional<BotMethodType> optionalType = typeRepo.findById(id);
		if(optionalType.isPresent()) {
		logger.debug("Retrieve Method type by id "+id);	
		return new ResponseEntity<>(optionalType.get(), HttpStatus.OK);
		}else {
		logger.warn("No Method type found by this id "+id);	
		throw new NotFoundHandler();	
		}
	}
	
	
	//Insert new Type 
	public ResponseEntity<BotMethodType> insertType(BotMethodType type ){
		if (type != null) {
			logger.debug("Insert new messgae Type "+type.toString());
			typeRepo.save(type);
			return new ResponseEntity<>(type,HttpStatus.CREATED);
		}else {
			logger.error("Invalid data for method type null value ");
			throw new UnprocessablEHandler();
		}
	}
	
	//Update existing message type
	public ResponseEntity<BotMethodType> updateExistingType(BotMethodType type){
		if (type != null && type.getMethodTypeId() != null) {
			Optional<BotMethodType> optionalType = typeRepo.findById(type.getMethodTypeId());
			if(optionalType.isPresent()) {
			BotMethodType existingType = optionalType.get();
			existingType.setMethodTypeId(type.getMethodTypeId());
			existingType.setMethodTypeName(type.getMethodTypeName());
			typeRepo.save(type);
			return new ResponseEntity<>(type, HttpStatus.ACCEPTED);
			}else {
				logger.warn("No method type found for updating ");
				throw new NotFoundHandler();
			}
		} else {
			logger.error("Invalid data for method type null value");
			throw new UnprocessablEHandler();
		}
	}
	
	//Delete existing message type
	public ResponseEntity<Void> deleteMessageType(Long id){
		boolean isExist = typeRepo.existsById(id);
		if(isExist) {
			logger.debug("Delete method type by id "+id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			logger.debug("No method type found for deleting "+id);
			throw new NotFoundHandler();
		}
	}
}
