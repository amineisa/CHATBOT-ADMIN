package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.WebServiceResponseType;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.WebServiceResponseTypeRepo;

@Service
public class WebServiceResponseTypeService {

	@Autowired
	private WebServiceResponseTypeRepo wsResponseType;
	
	private static final Logger logger = LoggerFactory.getLogger(WebServiceResponseTypeService.class);
	
	//Retrieve All types 
	public ResponseEntity<List<WebServiceResponseType>> getAllTypes(){
		List<WebServiceResponseType> types = wsResponseType.findAllByisDeletedFalse();
		logger.debug("Return all response types");
		return new ResponseEntity<>(types, HttpStatus.OK);
	}
	
	//Retrieve Specific Type By id 
	public ResponseEntity<WebServiceResponseType> getSpecificTypeById(Long id ){
		WebServiceResponseType type = wsResponseType.findByIsDeletedFalseAndResponseTypeId(id);
		if(type != null) {
			logger.debug("Return webservice out type by id "+id);
			return new ResponseEntity<>(type,HttpStatus.OK);
		}else {
			logger.warn("No Webservice type found by id "+id);
			throw new NotFoundHandler("No response type found by id "+id);
		}
	}
	
	//Insert new WebService out type
	public ResponseEntity<WebServiceResponseType> insertNewWsType(WebServiceResponseType type){
		if(type!= null) {
			logger.debug("Insert new WebService out type "+type.toString());
			type.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			type.setIsDeleted(false);
			wsResponseType.save(type);
			return new ResponseEntity<>(type, HttpStatus.CREATED);
		}else {
			logger.error("Invalid data for webservice out type insertion null values");
			throw new UnprocessablEHandler("Invalid data for response type insertion check required values");
		}
	}
	
	//Update existing WebService type 
	public ResponseEntity<WebServiceResponseType> updateExistWsType(WebServiceResponseType type){
		if(!type.checkEmpty()) {
			WebServiceResponseType existingType = wsResponseType.findByIsDeletedFalseAndResponseTypeId(type.getResponseTypeId());
			if(existingType != null) {
				existingType.setResponseTypeId(type.getResponseTypeId());
				existingType.setResponseTypeName(type.getResponseTypeName());
				wsResponseType.save(existingType);
				return new ResponseEntity<>(existingType, HttpStatus.ACCEPTED);
			}else {
				logger.warn("No response type found for updating ");
				throw new NotFoundHandler("No response type found for updating ");
			}
			
		}else {
			logger.error("Invalid data for webservice out type insertion null values");
			throw new UnprocessablEHandler();
		}
	}
	
	//Delete Existing WebService Out Type
	public ResponseEntity<Void> deleteExistingType(Long id){
		WebServiceResponseType existingType = wsResponseType.findByIsDeletedFalseAndResponseTypeId(id);
		if(existingType != null) {
			logger.debug("Delete WS out type by id "+id);
			existingType.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			existingType.setIsDeleted(true);
			wsResponseType.save(existingType);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			logger.warn("No response type exist "+id);
			throw new NotFoundHandler("No response type exist "+id);
		}
	}
}
