package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotConfiguration;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.ConfigurationRepo;

@Service
public class ConfigurationService {

	private ConfigurationRepo configurationRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
	
	//Retrieve all configuration rows
	public ResponseEntity<List<BotConfiguration>> getAllRows(){
		List<BotConfiguration> body = configurationRepo.findAllByIsDeletedFalse();
		logger.debug("Retrieve all configuration rows ");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	//Retrieve specific row 
	public ResponseEntity<BotConfiguration> getOneRow(Long id){
		BotConfiguration config = configurationRepo.findByIsDeletedFalseAndId(id);
		if(config != null) {
		logger.debug("Retrive Configration row "+config.toString());	
		return new ResponseEntity<>(config , HttpStatus.OK);
		}else {
		logger.warn("No configuration row found by this id "+id);	
		throw new NotFoundHandler("No configuration row found by this id "+id);
		}
	}
	
	//Insert new configuration row  
	public ResponseEntity<BotConfiguration> insertNewRow(BotConfiguration botConfiguration){
		if(botConfiguration.checkEmpty()) {
			logger.debug("Insert new configuration row {} "+botConfiguration.toString());
			botConfiguration.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			botConfiguration.setIsDeleted(false);
			configurationRepo.save(botConfiguration);
			return new ResponseEntity<>(botConfiguration, HttpStatus.CREATED);
		}else {
			logger.warn("Invalid data for configuration insertion ");
			throw new UnprocessablEHandler("Invalid data for configuration insertion check required values ");
		}
	}
	
	//Update existing Configuration row 
	public ResponseEntity<BotConfiguration> updateExistigRow(BotConfiguration configuration){
		if(configuration.checkEmpty() && configuration.getId() != null) {
			BotConfiguration existingConfiguration = configurationRepo.findByIsDeletedFalseAndId(configuration.getId());
			if(existingConfiguration != null) {
			logger.debug("Configuration updating new  value "+configuration.toString());
			logger.debug("Configuration updating existing value "+existingConfiguration.toString());
			existingConfiguration.setKey(configuration.getKey());
			existingConfiguration.setValue(configuration.getValue());
			configurationRepo.save(existingConfiguration);
			return new ResponseEntity<>(existingConfiguration,HttpStatus.ACCEPTED);
			}else {
				logger.warn("NO configuration row found for updating process");
				throw new NotFoundHandler("NO configuration row found for updating process");
			}
		}else {
			logger.debug("Invalid data for updating configuration row ");
			throw new UnprocessablEHandler("Invalid data for updating configuration check required values ");
		}
	}
	
	// Delete Existing row 
	public ResponseEntity<Void> deleteExistingRow(Long id){
		BotConfiguration existingConfig = configurationRepo.findByIsDeletedFalseAndId(id);
		if(existingConfig != null) {
			logger.debug("Delete configuration row by id "+id);
			existingConfig.setIsDeleted(true);
			existingConfig.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			configurationRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			logger.warn("No Configuration row exists by this id "+id);
			throw new NotFoundHandler("No Configuration row exists by this id "+id);
		}
	}
}
