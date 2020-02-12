package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotGTemplateMessage;
import com.chatbot.admin.entities.BotTemplateElement;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotElementRepo;
import com.chatbot.admin.repositories.GenericTemplateRepo;

@Service
public class BotElementService {
	
	@Autowired
	private BotElementRepo elementRepo;
	@Autowired
	private GenericTemplateRepo templateRepo;

	private static final Logger logger = LoggerFactory.getLogger(BotElementService.class);

	// Retrieve All Elements
	public ResponseEntity<List<BotTemplateElement>> getAllElements() {
		List<BotTemplateElement> body = elementRepo.findAllByIsDeletedFalse();
		return new ResponseEntity<>(body , HttpStatus.OK);
	}

	// Retrieve Element by its id
	public ResponseEntity<BotTemplateElement> getElementById(Long elementId) {
			BotTemplateElement element = elementRepo.findByIsDeletedFalseAndElementId(elementId);
			if (element != null) {
				logger.debug("Retrieve Element which its id is :" + elementId);
				return new ResponseEntity<>(element , HttpStatus.OK);
			} else {
				logger.debug("No element found with this id : "+elementId);
				throw new NotFoundHandler("No element found with this id");
			}
		
	}

	// Retrieve All Elements By Generic Template Id
	public ResponseEntity<List<BotTemplateElement>> getElementsByTemplateID(Long templateId) {
			BotGTemplateMessage gTemplate = templateRepo.findByIsDeletedFalseAndTemplateId(templateId);
			logger.debug("Retrieve Element List which are related to Generic template " + templateId);
			if (gTemplate != null ) {
				List<BotTemplateElement> body = elementRepo.findAllByIsDeletedFalseAndBotGTemplateMessage(gTemplate);
				return new ResponseEntity<>(body, HttpStatus.OK);
			} else {
				logger.debug("No Generic Template found : "+templateId);
				throw new NotFoundHandler("No Generic Template found : "+templateId);
			}
	}

	// Insert new Element
	public ResponseEntity<BotTemplateElement> insertNewElement(BotTemplateElement element) {
		if (checkElementValidity(element)) {
			element.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			element.setIsDeleted(false);
			element.setBotGTemplateMessage(templateRepo.findByIsDeletedFalseAndTemplateId(element.getTemplateId()));
			element.getBotButtons().forEach(button ->{
				button.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				button.setIsDeleted(false);
				button.setBotTemplateElement(element);
			});
			BotTemplateElement savedElement = elementRepo.save(element);
			logger.debug("Insert new element "+savedElement.toString());
			return new ResponseEntity<>(savedElement, HttpStatus.CREATED);
		} else {
			logger.debug("Invalid data for insertion new element object ");
			throw new UnprocessablEHandler("check that not empty button list and doesn't contain more than 3 buttons or title and subtile is required ");
		}
	}

	private boolean checkElementValidity(BotTemplateElement element) {
		boolean valid = false;
		if(element.getTitle().getArabicText().length() > 0 && element.getSubTitle().getArabicText().length()>0 && !element.getBotButtons().isEmpty()	
			&& element.getTemplateId() != null && element.getBotButtons().size()<3 	
			&&templateRepo.findByIsDeletedFalseAndTemplateId(element.getTemplateId()) != null) {
			valid = true;
		}
		
		return valid;
	}

	// Update an exist Element
	public ResponseEntity<BotTemplateElement> updateElement(BotTemplateElement element) {
		if (checkElementValidity(element) && elementRepo.findByIsDeletedFalseAndElementId(element.getElementId()) != null ) {
			BotTemplateElement existingElement = elementRepo.findByIsDeletedFalseAndElementId(element.getElementId());
				existingElement.setBotButtons(element.getBotButtons());
				existingElement.setBotGTemplateMessage(templateRepo.findByIsDeletedFalseAndTemplateId(element.getTemplateId()));
				existingElement.setIsDeleted(false);
				BotTemplateElement savedElement = elementRepo.save(existingElement);
				return new ResponseEntity<>(savedElement, HttpStatus.ACCEPTED);
			} else {
			logger.error("Invalid Null data  ");
			throw new UnprocessablEHandler("Check invalid data");
		}
	}

	// delete an Exist Element
	public ResponseEntity<Void> deleteElement(Long elementId) {
			logger.debug("Delete Element by its id "+elementId);
			BotTemplateElement element = elementRepo.findByIsDeletedFalseAndElementId(elementId);
			if (element != null ) {
				element.setIsDeleted(true);
				element.setDeletedDate(new Timestamp(System.currentTimeMillis()));
				element.getBotButtons().forEach(button -> {
					button.setIsDeleted(true);
					button.setDeletedDate(new Timestamp(System.currentTimeMillis()));
				});
				elementRepo.save(element);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				logger.debug("NO Element found");
				throw new NotFoundHandler("NO Element found with this "+elementId);
			}
		}

}
