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

import com.chatbot.admin.entities.BotButtonTemplateMSG;
import com.chatbot.admin.entities.BotGTemplateMessage;
import com.chatbot.admin.entities.BotInteraction;
import com.chatbot.admin.entities.BotQuickReplyMessage;
import com.chatbot.admin.entities.BotTextMessage;
import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotButtonRepo;
import com.chatbot.admin.repositories.BotElementRepo;
import com.chatbot.admin.repositories.BotInteractionMessageRepo;
import com.chatbot.admin.repositories.ButtonTemplateRepo;
import com.chatbot.admin.repositories.GenericTemplateRepo;
import com.chatbot.admin.repositories.InteractionRepo;
import com.chatbot.admin.repositories.MessageTypeRepo;
import com.chatbot.admin.repositories.QuickReplyTemplateRepo;
import com.chatbot.admin.repositories.TextMessageRepo;

@Service
public class InteractionMessageService {

	private static final Logger logger = LoggerFactory.getLogger(InteractionMessageService.class);

	@Autowired
	private BotInteractionMessageRepo interactionMessageRepo;
	@Autowired
	private InteractionRepo interactionRepo;
	@Autowired
	private MessageTypeRepo messageTypeRepo;
	@Autowired
	private ButtonTemplateRepo buttonTemplateRepo;
	@Autowired
	private GenericTemplateRepo genericTemplateRepo;
	@Autowired
	private TextMessageRepo textMessageRepo;
	@Autowired
	private QuickReplyTemplateRepo quickReplyTemplateRepo;
	@Autowired
	private BotButtonRepo buttonRepo;
	@Autowired
	private BotElementRepo elementRepo;
	

	// retrieve all messages
	public ResponseEntity<List<InteractionMessage>> getAllMessages() {
		List<InteractionMessage> messages = interactionMessageRepo.findAllBotInteractionMessagesByisDeletedFalse();
		List<InteractionMessage> body = new ArrayList<>();
		messages.forEach(message ->{
			message.setInteractionId(message.getBotInteraction().getInteractionId());
			body.add(message);
		});
		return new ResponseEntity<>(body , HttpStatus.OK);

	}

	// retrieve all messages which assigned to specific interaction
	public ResponseEntity<List<InteractionMessage>> getAllMessagesByInteractionId(Long interactionId) {
			logger.debug("Retrieve all messages for interaction " + interactionId);
			BotInteraction interaction = interactionRepo.findByIsDeletedFalseAndInteractionId(interactionId);
			if(interaction != null){
				List<InteractionMessage> body =  new ArrayList<>();
				List<InteractionMessage> messages = interactionMessageRepo.findBotInteractionMessagesByIsDeletedFalseAndBotInteraction(interaction);
				messages.forEach(message ->{
					message.setInteractionId(message.getBotInteraction().getInteractionId());
					body.add(message);
				});
				
				return new ResponseEntity<>(body,HttpStatus.OK);	
			}else {
				logger.debug("No Interaction found by this id ");
				throw new NotFoundHandler("No Interaction found by this id ");
			}			
	}

	// retrieve specific message by its ID
	public ResponseEntity<InteractionMessage> getMessageByItsId(Long messageId) {
			InteractionMessage message = interactionMessageRepo.findByIsDeletedFalseAndMessageId(messageId);
			if (message != null ) {
				logger.debug("Retrieve message   :" + message.toString());
				message.setInteractionId(message.getBotInteraction().getInteractionId());
				return new ResponseEntity<>(message, HttpStatus.OK);
			} else {
				throw new NotFoundHandler();
			}

		}


	// Insert new Message
	public ResponseEntity<InteractionMessage> insertNewMessage(InteractionMessage message) {
		if (message.getInteractionId() != null && messageTypeRepo.findById(message.getMessageTypeId()).isPresent()) {
			if(interactionRepo.existsById(message.getInteractionId())) {
				message.setBotInteraction(interactionRepo.findByIsDeletedFalseAndInteractionId(message.getInteractionId()));
				message.setIsDeleted(false);
				message.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				message.setBotMessageType(messageTypeRepo.findById(message.getMessageTypeId()).get());
				logger.debug("Insert new Message " + message.toString());
				return new ResponseEntity<>(interactionMessageRepo.save(message),HttpStatus.CREATED);
			}else {
				throw new NotFoundHandler("you try to add message for unexisting interaction ");
			}
			
		} else {
			logger.debug("you can not insert null values ");
			throw new UnprocessablEHandler();
		}
	}

	// update an existing message
	public ResponseEntity<InteractionMessage> updateExistMessage(InteractionMessage message) {
		InteractionMessage existingMessage = interactionMessageRepo.findByIsDeletedFalseAndMessageId(message.getMessageId());
		if ( existingMessage != null ){
			if(message.getInteractionId() != null && interactionRepo.findByIsDeletedFalseAndInteractionId(message.getInteractionId()) != null) {
				existingMessage.setBotInteraction(existingMessage.getBotInteraction());
				existingMessage.setBotMessageType(messageTypeRepo.findByIsDeletedFalseAndMessageTypeId(message.getMessageTypeId()));
				existingMessage.setIsStatic(message.getIsStatic());
				existingMessage.setMessageId(message.getMessageId());
				existingMessage.setMessagePriority(message.getMessagePriority());
				existingMessage.setIsDeleted(false);
				existingMessage.setCreatedDate(existingMessage.getCreatedDate());
				existingMessage.setBotInteraction(interactionRepo.findByIsDeletedFalseAndInteractionId(message.getInteractionId()));
				//logger.debug("Update message : "+existingMessage.toString());
				return new ResponseEntity<>(interactionMessageRepo.save(existingMessage),HttpStatus.ACCEPTED);	
			}else {
				throw new NotFoundHandler("No interaction found by this id");
			}
		} else {
			logger.debug("You can't update message with null value ");
			throw new NotFoundHandler("You can't update message with null value");
		}
		

	}

	// delete an exist message
	public ResponseEntity<Void> deleteMessage(Long messageId) {
		InteractionMessage existingMessage = interactionMessageRepo.findByIsDeletedFalseAndMessageId(messageId);
		if (existingMessage != null) {
			logger.debug("Delete an existing message " + existingMessage.toString());
			existingMessage.setIsDeleted(true);
			deleteMessageTemplate(existingMessage);
			existingMessage.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			interactionMessageRepo.save(existingMessage);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.debug("You try to delete an unexisting message ");
			throw new NotFoundHandler();
		}
	}

	
	
	public void deleteMessageTemplate(InteractionMessage message) {
		Timestamp deletionDate = new Timestamp(System.currentTimeMillis());
		switch (message.getBotMessageType().getMessageTypeName()) {
		case "BUTTONTEMPLATE":
			BotButtonTemplateMSG template = buttonTemplateRepo.findBotButtonTemplateMSGByIsDeletedFalseAndInteractionMessage(message);
			if(template != null) {
			template.setIsDeleted(true);
			template.setDeletedDate(deletionDate);
			template.getButtons().forEach(button ->{
				button.setDeletedDate(deletionDate);
				button.setIsDeleted(true);
				buttonRepo.save(button);
			});
			buttonTemplateRepo.save(template);			
			break;
			}
			return ;
		case "GENERICTEMPLATEMESSAGE":
			BotGTemplateMessage gTemplateMessage =  genericTemplateRepo.findByIsDeletedFalseAndInteractionMessage(message);
			if(gTemplateMessage !=null ) {
			gTemplateMessage.getBotTemplateElements().forEach(element ->{
				element.setIsDeleted(true);
				element.setDeletedDate(deletionDate);
				element.getBotButtons().forEach(button ->{
					button.setIsDeleted(true);
					button.setDeletedDate(deletionDate);
					buttonRepo.save(button);
				});
				elementRepo.save(element);
			});
		   gTemplateMessage.setDeletedDate(deletionDate);
		   gTemplateMessage.setIsDeleted(true);
		   genericTemplateRepo.save(gTemplateMessage);
		   break;
			}
			return;
		case "QUICKREPLYMESSAGE":
			BotQuickReplyMessage quickReplyMessage = quickReplyTemplateRepo.findBotByIsDeletedFalseAndInteractionMessage(message);
			if(quickReplyMessage != null) {
			quickReplyMessage.getBotButtons().forEach(button ->{
				button.setIsDeleted(true);
				button.setDeletedDate(deletionDate);
				buttonRepo.save(button);
			});
			quickReplyMessage.setDeletedDate(deletionDate);
			quickReplyMessage.setIsDeleted(true);
			quickReplyTemplateRepo.save(quickReplyMessage);
		break;
		}
			return;
		case "TEXTMESSAGE":
			BotTextMessage textMsg = textMessageRepo.findByIsDeletedFalseAndInteractionMessage(message);
			if (textMsg != null ) {
			textMsg.setCreatedDate(deletionDate);
			textMsg.setIsDeleted(true);
			textMessageRepo.save(textMsg);
			break;	
			}
		return;
		}		
	}
	
	 

}
