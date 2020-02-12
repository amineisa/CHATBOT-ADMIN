package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotText;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotTextRepo;

@Service
public class BotTextService {

	@Autowired
	private BotTextRepo botTextRepo;



	private static final Logger logger = LoggerFactory.getLogger(BotTextService.class);

	// Retrieve all texts
	public ResponseEntity<List<BotText>> getAllTexts() {
		logger.debug("Retrieve All texts ");
		return new ResponseEntity<>(botTextRepo.findAllByIsDeletedFalse(), HttpStatus.OK);
	}

	// Retrieve specific text by its id
	public ResponseEntity<BotText> getTextByID(Long textId) {
			BotText text = botTextRepo.findByIsDeletedFalseAndTextId(textId);
			if (text != null) {
				logger.debug("Get text object which id is " + textId);
				return new ResponseEntity<>(text , HttpStatus.OK);
			} else {
				logger.warn("You try to retrieve not exist object");
				throw new NotFoundHandler();
			}
		
	}
	
	
	
	// Insert new Text
	public ResponseEntity<BotText> insertNewText(BotText text) {
		if (text != null) {
			logger.debug("Insert new Text "+text.toString());
			text.setIsDeleted(false);
			text.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(botTextRepo.save(text), HttpStatus.CREATED);
		} else {
			logger.warn("You can not insert null values");
			throw new UnprocessablEHandler("You can not insert null values");
		}
	}

	// Update an existing text
	public ResponseEntity<BotText> updateAnExistingText(BotText text) {
		if(text.getTextId() != null && text.getArabicText() != null && text.getEnglishText() != null ) {
			BotText existingText = botTextRepo.findByIsDeletedFalseAndTextId(text.getTextId());
		if (existingText != null) {
			existingText.setArabicText(text.getArabicText());
			existingText.setEnglishText(text.getEnglishText());
			existingText.setTextId(text.getTextId());
			botTextRepo.save(existingText);
			logger.debug("Update text {} :"+existingText);
			return new ResponseEntity<>(existingText, HttpStatus.ACCEPTED);
		} else {
			logger.warn("you try to update an unexisting text");
			throw new NotFoundHandler("No text existing to be updated ");
		}			
		}else {
			logger.warn("you try to update an unexisting text");
			throw new UnprocessablEHandler("Null values check all required values  ");
		}

	}

	// Delete an existing text
	/*public ResponseEntity<Void> deleteAnExistText(Long id) {
			boolean isExist = botTextRepo.existsById(id);
			if (isExist) {
				logger.debug("you try to delete text object which its id is " + id);
				botTextRepo.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				logger.warn("you try to delete an unexisting text");
				throw new NotFoundHandler();
			}
	}*/
	
	
}
