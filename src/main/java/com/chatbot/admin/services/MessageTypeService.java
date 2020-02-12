package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotMessageType;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.MessageTypeRepo;

@Service
public class MessageTypeService {

	@Autowired
	private MessageTypeRepo typeRepo;
	
	private static final  Logger logger =LoggerFactory.getLogger(MessageTypeService.class); 
	
	//Retrieve all types of message
	public ResponseEntity<List<BotMessageType>> getAllTypes(){
		List<BotMessageType> body = typeRepo.findAllByIsDeletedFalse();
		logger.debug("All messages types");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	//Retrieve type by its id 
	public ResponseEntity<BotMessageType> getTypeById(Long typeId){
		BotMessageType messageType = typeRepo.findByIsDeletedFalseAndMessageTypeId(typeId);
		if(messageType != null ) {
			logger.debug("Retrieve type by id "+typeId);
			return new ResponseEntity<>(messageType, HttpStatus.OK);
		}else {
			logger.warn("No Type found by this id "+typeId);
			throw new NotFoundHandler("No message type found by this id "+typeId);
		}
	}
	
	//Insert new Type 
	public ResponseEntity<BotMessageType> insertType(BotMessageType type ){
		if (type != null) {
			logger.debug("Insert new messgae Type "+type.toString());
			type.setIsDeleted(false);
			type.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(typeRepo.save(type),HttpStatus.CREATED);
		}else {
			logger.error("Invalid data for messagetype null value ");
			throw new UnprocessablEHandler("Invalid data for insert new message type");
		}
	}
	
	//Update existing message type
	public ResponseEntity<BotMessageType> updateExistingType(BotMessageType type){
		if (type.checkEmpty() && type.getMessageTypeId() != null ) {
			BotMessageType existType = typeRepo.findByIsDeletedFalseAndMessageTypeId(type.getMessageTypeId());
			if(existType != null) {
				logger.debug("Insert new message type "+type.toString());
				existType.setMessageTypeName(type.getMessageTypeName());
			return new ResponseEntity<>(typeRepo.save(existType), HttpStatus.ACCEPTED);
			}else {
				logger.warn("No message type found for updating ");
				throw new NotFoundHandler("No message type found for updating ");
			}
		} else {
			logger.error("Invalid data for messagetype null value");
			throw new UnprocessablEHandler("Invalid data for messagetype null value");
		}
	}
	
	//Delete existing message type
	public ResponseEntity<Void> deleteMessageType(Long id){
		BotMessageType existingType = typeRepo.findByIsDeletedFalseAndMessageTypeId(id);
		if(existingType != null ) {
			logger.debug("Delete message type by id "+id);
			existingType.setIsDeleted(true);
			existingType.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			typeRepo.save(existingType);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			logger.debug("No message type found for deleting "+id);
			throw new NotFoundHandler("No message type found for deleting "+id);
		}
	} 
	
	}
