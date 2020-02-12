package com.chatbot.admin.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotTextResponseMapping;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.TextResponseMappingRepo;

@Service
public class TextResponseMappingService {

	 @Autowired
	 private TextResponseMappingRepo textResponseRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(TextResponseMappingService.class);
	
	//Retrieve all Text Response mapping 
	public ResponseEntity<List<BotTextResponseMapping>> getAllTextResponseMapping(){
		List<BotTextResponseMapping> body = textResponseRepo.findAll();
		return new ResponseEntity<>(body , HttpStatus.OK);
	} 
	
	//Retrieve Specific Text Response mapping  by id 
	public ResponseEntity<BotTextResponseMapping> getTextResponseMapping(Long id){
		Optional<BotTextResponseMapping> optionalResponse = textResponseRepo.findById(id);
		if(optionalResponse.isPresent()) {
			logger.debug("Return text response mapping by id "+id);
			return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
		}else {
			logger.warn("Text mapping NOt found by id "+id);
			throw new NotFoundHandler();
		}
	}
	
	//Insert new Text response mapping 
	public ResponseEntity<BotTextResponseMapping> insertNewMapping(BotTextResponseMapping mapping){
		if(mapping != null) {
			logger.debug("Insert new Text response mapping "+mapping.toString());
			textResponseRepo.save(mapping);
			return new ResponseEntity<>(mapping, HttpStatus.CREATED);
		}else {
			logger.error("Invalid value for Text response mapping null value ");
			throw new UnprocessablEHandler();
		}
	}
	
	//Update existing Text response mapping
	public ResponseEntity<BotTextResponseMapping> updateMapping(BotTextResponseMapping mapping){
		if(mapping != null && mapping.getRsMapId() != null ) {
			Optional<BotTextResponseMapping> optionalMapping = textResponseRepo.findById(mapping.getRsMapId());
			if(optionalMapping.isPresent()) {
				BotTextResponseMapping existingMapping = optionalMapping.get();
				logger.debug("Update text response mapping "+mapping.toString());
				existingMapping.setRsMapId(mapping.getRsMapId());
				existingMapping.setArParams(mapping.getArParams());
				existingMapping.setBotText(mapping.getBotText());
				existingMapping.setBotWebserviceMessage(mapping.getBotWebserviceMessage());
				existingMapping.setCommonPath(mapping.getCommonPath());
				existingMapping .setEnParams(mapping.getEnParams());
				textResponseRepo.save(existingMapping);
				return new ResponseEntity<>(mapping , HttpStatus.ACCEPTED);
			}else {
				logger.warn("Text response mapping Not found "+mapping.toString());
				throw new NotFoundHandler();
			}
		}else {
			logger.error("Invalid value for bot text response Null values ");
			throw new UnprocessablEHandler();
		}
	}
	
	//Delete Existing Text Response Mapping
	public ResponseEntity<Void> deleteExistingMapping(Long id){
		boolean isExist = textResponseRepo.existsById(id);
		if(isExist) {
			logger.debug("Delete existing mapping by "+id);
			textResponseRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			logger.warn("Text Response Mapping Not Found for deleting "+id);
			throw new NotFoundHandler();
		}
	}
}
