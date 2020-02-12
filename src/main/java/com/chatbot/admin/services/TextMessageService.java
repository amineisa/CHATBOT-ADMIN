package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotTextMessage;
import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotInteractionMessageRepo;
import com.chatbot.admin.repositories.TextMessageRepo;

@Service
public class TextMessageService {

	@Autowired
	private TextMessageRepo textMsgRepo;
	@Autowired
	private BotInteractionMessageRepo messageRepo;

	private static final Logger logger = LoggerFactory.getLogger(TextMessageService.class);

	// Retrieve All Text messages
	public ResponseEntity<List<BotTextMessage>> allTextMessages() {
		List<BotTextMessage> messages = textMsgRepo.findAllByIsDeletedFalse();
		List<BotTextMessage> body = new ArrayList<>();
		messages.forEach(message -> {
			message.setMessageId(message.getInteractionMessage().getMessageId());
			body.add(message);
		});
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	// Retrieve Specific Text message by its id
	public ResponseEntity<BotTextMessage> textMsgById(Long textMsgId) {
		BotTextMessage textMsg = textMsgRepo.findByIsDeletedFalseAndTextMsgId(textMsgId);
		if (textMsg != null) {
			textMsg.setMessageId(textMsg.getInteractionMessage().getMessageId());
			logger.debug("Retrieve Text Message " + textMsg.toString());
			return new ResponseEntity<>(textMsg, HttpStatus.OK);
		} else {
			logger.warn("No text message found with this id " + textMsgId);
			throw new NotFoundHandler("No text message found with this id " + textMsgId);
		}

	}

	// Retrieve specific message by interaction message id
	public ResponseEntity<BotTextMessage> textMsgByMessageId(Long msgId) {
		InteractionMessage interactionMessage = messageRepo.findByIsDeletedFalseAndMessageId(msgId);
		if (interactionMessage != null ){
			if(textMsgRepo.findByIsDeletedFalseAndInteractionMessage(interactionMessage) != null) {
			BotTextMessage textMessage = textMsgRepo.findByIsDeletedFalseAndInteractionMessage(interactionMessage);
			textMessage.setMessageId(textMessage.getInteractionMessage().getMessageId());
			logger.debug("Retrieve Text Messsage which belongs to message " + textMessage.toString());
			return new ResponseEntity<>(textMessage, HttpStatus.OK);
			}else {
				logger.debug("No Text message found " );
				throw new NotFoundHandler("No message found ");
			}
		} else {
			logger.debug("No message found by this id " + msgId);
			throw new NotFoundHandler("No message found by this id :  "+msgId );
		}
	}

	// Insert new Text Message
	public ResponseEntity<BotTextMessage> insertNewTextMessage(BotTextMessage textMsg) {
		if (textMsg.getBotText().getArabicText().length() > 0 && textMsg.getMessageId() != null) {
			InteractionMessage message = messageRepo.findByIsDeletedFalseAndMessageId(textMsg.getMessageId());
			if(message != null ) {
			textMsg.setIsDeleted(false);
			textMsg.setIsStatic(true);
			textMsg.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			textMsg.setInteractionMessage(messageRepo.findByIsDeletedFalseAndMessageId(textMsg.getMessageId()));
			BotTextMessage savedMsg = textMsgRepo.save(textMsg);
			return new ResponseEntity<>(savedMsg, HttpStatus.CREATED);
			}else {
				logger.warn("No interaction message exist with this id ");
				throw new UnprocessablEHandler("Invalid data for insertion text messgae no interaction message exist with this id ");
			}
		} else {
			logger.warn("Invalid data for insertion text messgae ");
			throw new UnprocessablEHandler("Invalid data for insertion text messgae text is required");
		}
	}

	// Update an existing text template
	public ResponseEntity<BotTextMessage> updateExistingTextMessage(BotTextMessage txtMsg) {
		if (checkMessageValidity(txtMsg)) {
			BotTextMessage existingMsg = textMsgRepo.findByIsDeletedFalseAndTextMsgId(txtMsg.getTextMsgId());
			InteractionMessage  message = messageRepo.findByIsDeletedFalseAndMessageId(txtMsg.getMessageId()); 
			if(message != null && existingMsg != null ) {
				existingMsg.setIsDeleted(false);
				existingMsg.setInteractionMessage(message);
				existingMsg.setBotText(txtMsg.getBotText());
				BotTextMessage body = textMsgRepo.save(existingMsg);
				logger.debug("updated Text message "+body.toString());
				return new ResponseEntity<>(body , HttpStatus.ACCEPTED);
			}else {
				logger.warn("No interaction message exist by this id");
				throw new NotFoundHandler("No interaction message exist by this id ");
			}
		} else {
			logger.warn("Invalid data check null vales {text or messageId} ");
			throw new UnprocessablEHandler("Invalid data check null vales {text or messageId} ");
		}
	}

	private boolean checkMessageValidity(BotTextMessage txtMsg) {
		boolean valid = false;
		if(txtMsg != null && txtMsg.getBotText().getArabicText().length()>0 && txtMsg.getMessageId() != null) {
			 valid = true;
		}
		return valid;
	}

	// Delete an existing text message
	public ResponseEntity<Void> deleteExistingTxtMsg(Long id) {
		BotTextMessage textMsg = textMsgRepo.findByIsDeletedFalseAndTextMsgId(id);
		if (textMsg != null) {
			logger.debug("Deleting text message by id " + id);
			textMsg.setIsDeleted(true);
			textMsg.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			textMsgRepo.save(textMsg);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.warn("No text message found by this id " + id);
			throw new NotFoundHandler("No text message found by this id " + id);
		}
	}

	

}
