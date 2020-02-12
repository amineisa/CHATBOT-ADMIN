package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotQuickReplyMessage;
import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotInteractionMessageRepo;
import com.chatbot.admin.repositories.QuickReplyTemplateRepo;

@Service
public class QuickReplyTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(QuickReplyTemplateService.class);
	@Autowired
	private QuickReplyTemplateRepo quickReplyRepo;
	@Autowired
	private BotInteractionMessageRepo messageRepo;
	
	// Retrieve All Quick Reply templates
	public ResponseEntity<List<BotQuickReplyMessage>> getAllQuickReplyTemplates(){
		List<BotQuickReplyMessage> body = quickReplyRepo.findAllByIsDeletedFalse();
		return new ResponseEntity<>(body , HttpStatus.OK);
	}
	
	// Retrieve specific Quick Reply
	public ResponseEntity<BotQuickReplyMessage> getQuickTemplateById(Long id){
		BotQuickReplyMessage template = quickReplyRepo.findByIsDeletedFalseAndQuickMsgId(id);
		if(template != null) {
			logger.debug("Retrieve Quick Template "+template.toString());
			return new ResponseEntity<>(template, HttpStatus.OK);
		}else {
			logger.warn("No template found By this id");
			throw new NotFoundHandler("No template found By this id");
		}
	}
	
	// Retrieve Quick Reply Template By Message id 
	public ResponseEntity<BotQuickReplyMessage> getQuickTemplateByMsgId(Long msgId){
		InteractionMessage message = messageRepo.findByIsDeletedFalseAndMessageId(msgId);
		if(message != null) {
			BotQuickReplyMessage template = quickReplyRepo.findBotByIsDeletedFalseAndInteractionMessage(message);
			if(template != null) {
				logger.debug("Retrieve Quickreply");
			return new ResponseEntity<>(template , HttpStatus.OK);
			}else {
			throw new NotFoundHandler("No Template found by this message ");
			}	
		}else {
			logger.warn("No message found by this ID");
			throw new NotFoundHandler("No message found by this ID");
		}
		
	}
	
	//Insert new Quick Reply Template 
	public ResponseEntity<BotQuickReplyMessage> insertNewTemplte(BotQuickReplyMessage quickTemplate){
		if(checkTemplateValidity(quickTemplate)) {
			InteractionMessage message = messageRepo.findByIsDeletedFalseAndMessageId(quickTemplate.getMessageId());
			if(message != null) {
			quickTemplate.getBotButtons().forEach(button ->{
				button.setBotQuickReplyMessage(quickTemplate);
				button.setIsDeleted(false);
				button.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			});
			quickTemplate.setIsDeleted(false);
			quickTemplate.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			quickTemplate.setInteractionMessage(message);
			BotQuickReplyMessage savedTemplate = quickReplyRepo.save(quickTemplate);
			logger.debug("Saving Quick Template "+quickTemplate.toString());
			return new ResponseEntity<>(savedTemplate, HttpStatus.CREATED);
			}else {
				logger.debug("No message exist with this id { you try to set template to not existing message }");
				throw new NotFoundHandler("No message exist with this id { you try to set template to not existing message }");
			}
		}else {
			throw new UnprocessablEHandler("Check null values not accepted or button list more than 11 or less than 1");
		}
	}
	
	private boolean checkTemplateValidity(BotQuickReplyMessage quickTemplate) {
		boolean valid = false;
		if(quickTemplate!= null && quickTemplate.getMessageId() != null && 
				!quickTemplate.getBotButtons().isEmpty() && quickTemplate.getBotButtons().size() <11
				&& quickTemplate.getBotText().getArabicText().length() > 0) {
			valid = true;
		}
		return valid;
	}

	//Update an existing template
	public ResponseEntity<BotQuickReplyMessage> updateAnExistinfTemplate(BotQuickReplyMessage quickTemplate){
		if(quickTemplate != null && quickTemplate.getQuickMsgId() != null && quickTemplate.getMessageId()!= null ) {
			InteractionMessage message = messageRepo.findByIsDeletedFalseAndMessageId(quickTemplate.getMessageId());
			if(message != null) {
				BotQuickReplyMessage existingTemplate = quickReplyRepo.findBotByIsDeletedFalseAndInteractionMessage(message);
				existingTemplate.setBotButtons(quickTemplate.getBotButtons());
				existingTemplate.getBotButtons().forEach(button ->{
					Timestamp creationDate = button.getCreatedDate() == null ? new Timestamp(System.currentTimeMillis()) : button.getCreatedDate();
					button.setIsDeleted(false);
					button.setCreatedDate(creationDate);
					button.setBotQuickReplyMessage(existingTemplate);
				});
				existingTemplate.setIsDeleted(false);
				existingTemplate.setIsStatic(quickTemplate.getIsStatic());
				existingTemplate.setInteractionMessage(message);				  
				quickReplyRepo.save(existingTemplate);
				logger.debug("Updated QuickTeply Template is "+existingTemplate.toString());
				return new ResponseEntity<>(quickReplyRepo.getOne(quickTemplate.getQuickMsgId()), HttpStatus.ACCEPTED);
			}else {
				throw new NotFoundHandler("No message exist with this id ");
			} 
		}else {
			throw new UnprocessablEHandler("Check null values and required values");
		}
	}
	
	//Delete An Existing Template by its id
	public ResponseEntity<Void> deleteAnExistinfTemplate(Long templateId){
		BotQuickReplyMessage existingTemplate = quickReplyRepo.findByIsDeletedFalseAndQuickMsgId(templateId);
		if(existingTemplate != null ) {
			logger.warn("Deleting Quick Template by id "+templateId);
			existingTemplate.setIsDeleted(true);
			existingTemplate.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			existingTemplate.getBotButtons().forEach(button ->{
				button.setIsDeleted(true);
				button.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			});
			quickReplyRepo.save(existingTemplate);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new NotFoundHandler("No existing quick reply template found with this id");
		}
	}
}
