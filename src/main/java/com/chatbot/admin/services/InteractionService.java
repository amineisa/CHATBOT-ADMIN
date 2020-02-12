package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotInteraction;
import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.repositories.BotInteractionMessageRepo;
import com.chatbot.admin.repositories.InteractionRepo;

@Service
public class InteractionService {

	private static final Logger logger = LoggerFactory.getLogger(InteractionService.class);

	@Autowired
	private InteractionRepo interactionRepo; 
	@Autowired
	private BotInteractionMessageRepo interactionMessageRepo;
	@Autowired
	private InteractionMessageService messageService;
	
	// retrieve all Interactions
	public ResponseEntity<List<BotInteraction>> getAllInteractions() {
		logger.debug("Retrieve All Interactions ");
		List<BotInteraction> body = interactionRepo.findAllByisDeletedFalse();/*.forEach(interaction ->{
			List<InteractionMessage> messages = interactionMessageRepo.findBotInteractionMessagesByIsDeletedFalseAndBotInteraction(interaction);
			interaction.setInteractionMessages(messages);
			body.add(interaction);
		});*/
		return new ResponseEntity<>(body, HttpStatus.OK);

	}

	// retrieve specific interaction by its id
	public ResponseEntity<BotInteraction> getInteractionById(Long id) {
			logger.debug("Retrieve interaction which its id is " + id);
			BotInteraction interaction = interactionRepo.findByIsDeletedFalseAndInteractionId(id);
			if (interaction != null) {
				logger.debug("Retrieve Interaction : "+interaction.toString() );
				List<InteractionMessage> messages = interactionMessageRepo.findBotInteractionMessagesByIsDeletedFalseAndBotInteraction(interaction);
				interaction.setInteractionMessages(messages);
				return new ResponseEntity<>(interaction, HttpStatus.OK);
			} else {
				throw new NotFoundHandler();
			}
			}

	// insert new interaction
	public ResponseEntity<BotInteraction> saveInteraction(BotInteraction  interaction) {
		if (interaction != null) {
			Timestamp creationDate = new Timestamp(System.currentTimeMillis());
			interaction.setCreatedDate(creationDate);
//			List<InteractionMessage> messages = new ArrayList<>();
//			interaction.getInteractionMessages().forEach( message ->{
//				message.setCreatedDate(creationDate);
//				message.setBotInteraction(interaction);
//				message.setIsDeleted(false);
//				messages.add(message);
//			});
			interaction.setIsDeleted(false);
			//interaction.setInteractionMessages(messages);
			return new ResponseEntity<>(interactionRepo.save(interaction) , HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// delete specific interaction by its id
	public ResponseEntity<Void> deleteInteraction(Long  interactionId) {
		BotInteraction interaction = interactionRepo.findByIsDeletedFalseAndInteractionId(interactionId);
		Timestamp deletedDate = new Timestamp(System.currentTimeMillis());
		if (interaction != null) {
			interaction.setIsDeleted(true);
			interaction.setDeletedDate(deletedDate);
			interaction.setCreatedDate(interaction.getCreatedDate());
			 interactionMessageRepo.findBotInteractionMessagesByIsDeletedFalseAndBotInteraction(interaction).forEach(message -> {
				 message.setDeletedDate(deletedDate);
				 message.setIsDeleted(true);
				 message.setCreatedDate(message.getCreatedDate());
				 messageService.deleteMessageTemplate(message);
				 interactionMessageRepo.save(message);
			 });
			interactionRepo.save(interaction);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new NotFoundHandler();
		}
	}

	// update an exist interaction
	public ResponseEntity<BotInteraction> updateInteraction(BotInteraction interaction) {
		BotInteraction existingInteraction = interactionRepo.findByIsDeletedFalseAndInteractionId(interaction.getInteractionId());		
		if (existingInteraction != null ) {
			existingInteraction.setPayload(interaction.getPayload());
			existingInteraction.setInteractionId(interaction.getInteractionId());
			existingInteraction.setInteractionName(interaction.getInteractionName());
			existingInteraction.setParentPayload(interaction.getParentPayload());
			existingInteraction.setIsSecure(interaction.getIsSecure());
			existingInteraction.setIsDeleted(false);
			existingInteraction.setCreatedDate(existingInteraction.getCreatedDate());
			logger.debug("Update interaction : {} "+existingInteraction.toString());
			return new ResponseEntity<>(interactionRepo.save(existingInteraction), HttpStatus.OK);
		} else {
			throw new NotFoundHandler();
		}
		
	}

	
	
	
	
	
}
