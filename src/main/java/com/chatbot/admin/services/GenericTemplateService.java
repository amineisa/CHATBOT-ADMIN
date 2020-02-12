package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotButton;
import com.chatbot.admin.entities.BotGTemplateMessage;
import com.chatbot.admin.entities.BotTemplateElement;
import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotButtonRepo;
import com.chatbot.admin.repositories.BotElementRepo;
import com.chatbot.admin.repositories.BotInteractionMessageRepo;
import com.chatbot.admin.repositories.GenericTemplateRepo;

@Service
public class GenericTemplateService {

	@Autowired
	private GenericTemplateRepo gTemplateRepo;
	@Autowired
	private BotInteractionMessageRepo msgRepo;
	@Autowired
	private BotElementRepo elementRepo;
	@Autowired
	private BotButtonRepo buttonRepo;

	private static final Logger logger = LoggerFactory.getLogger(GenericTemplateService.class);

	// Retrieve All Generic Templates
	public ResponseEntity<List<BotGTemplateMessage>> getAllTemplates() {
		List<BotGTemplateMessage> body = gTemplateRepo.findAllByIsDeletedFalse();
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	// Retrieve specific Generic Template by its Id
	public ResponseEntity<BotGTemplateMessage> getTemplateById(Long id) {
		BotGTemplateMessage template = gTemplateRepo.findByIsDeletedFalseAndTemplateId(id);
		if (template != null) {
			logger.debug("Retrieve Generic Template  " + template.toString());
			return new ResponseEntity<>(template, HttpStatus.OK);
		} else {
			logger.debug("you try to retrieve an unexisting Template");
			throw new NotFoundHandler("No template found with this id " + id);
		}

	}

	// Retrieve specific Generic Template by Message ID
	public ResponseEntity<BotGTemplateMessage> getTemplateByMsgID(Long msgId) {
		InteractionMessage message = msgRepo.findByIsDeletedFalseAndMessageId(msgId);
		if (message != null) {
			BotGTemplateMessage body = gTemplateRepo.findByIsDeletedFalseAndInteractionMessage(message);
			if (body != null) {
				return new ResponseEntity<>(body, HttpStatus.OK);
			} else {
				throw new NotFoundHandler("No template found for this message " + msgId);
			}
		} else {
			throw new NotFoundHandler("No message found by this id " + msgId);
		}
	}

	// Insert new Generic template
	public ResponseEntity<BotGTemplateMessage> insertNewTemplate(BotGTemplateMessage gTemplate) {
		if (checkTemlateValues(gTemplate)) {
			logger.debug("Insert new Generic Template " + gTemplate.toString());
			Timestamp creationDate = new Timestamp(System.currentTimeMillis());
			gTemplate.setInteractionMessage(msgRepo.findByIsDeletedFalseAndMessageId(gTemplate.getMessageId()));
			gTemplate.setCreatedDate(creationDate);
			gTemplate.setIsDeleted(false);
			gTemplate.getBotTemplateElements().forEach(element -> {
				if (checkElementRequiredValues(element)) {
					element.setCreatedDate(creationDate);
					element.setIsDeleted(false);
					element.setBotGTemplateMessage(gTemplate);
					element.getBotButtons().forEach(button -> {
						if (checkButtonRequiredValues(button)) {
							button.setIsDeleted(false);
							button.setCreatedDate(creationDate);
							button.setBotTemplateElement(element);
						} else {
							throw new UnprocessablEHandler("check button required values");
						}
					});
				} else {
					throw new UnprocessablEHandler(
							"check element required values title and subtitle check if button list contains at least 1 button and not more than 3 buttons");
				}
			});
			return new ResponseEntity<>(gTemplateRepo.save(gTemplate), HttpStatus.CREATED);
		} else {
			logger.debug("you can not insert null values ");
			throw new UnprocessablEHandler();
		}

	}

	// Update an existing Template
	public ResponseEntity<BotGTemplateMessage> updateTemplate(BotGTemplateMessage gTemplate) {
		if (gTemplate.getTemplateId() != null && checkTemlateValues(gTemplate)) {
			BotGTemplateMessage existingTemplate = gTemplateRepo.findByIsDeletedFalseAndTemplateId(gTemplate.getTemplateId());
			if (existingTemplate != null) {
				logger.debug("Update Generic Templete " + gTemplate.toString());
				existingTemplate.setCreatedDate(existingTemplate.getCreatedDate());
				existingTemplate.setIsListTemplate(gTemplate.getIsListTemplate());
				existingTemplate.setBotTemplateElements(gTemplate.getBotTemplateElements());
				existingTemplate.setInteractionMessage(msgRepo.findByIsDeletedFalseAndMessageId(gTemplate.getMessageId()));
				existingTemplate.setIsDeleted(false);
				existingTemplate.getBotTemplateElements().forEach(element -> {
					Timestamp creationDate = element.getElementId() != null ? elementRepo.findByIsDeletedFalseAndElementId(element.getElementId()).getCreatedDate() : new Timestamp(System.currentTimeMillis()); 
					element.setCreatedDate(creationDate);
					element.setBotGTemplateMessage(existingTemplate);
					element.setIsDeleted(false);
					element.getBotButtons().forEach(button -> {
						Timestamp createdDate = button.getButtonId() != null ? buttonRepo.findByIsDeletedFalseAndButtonId(button.getButtonId()).get().getCreatedDate() : new Timestamp(System.currentTimeMillis());
						button.setCreatedDate(createdDate );
						button.setIsDeleted(false);
						button.setBotTemplateElement(element);
					});
				});
				BotGTemplateMessage savedTemplate = gTemplateRepo.save(existingTemplate);
				return new ResponseEntity<>(savedTemplate, HttpStatus.ACCEPTED);
			} else {
				logger.debug("No Generic Template Found ");
				throw new NotFoundHandler("No Generic Template Found ");
			}
		} else {
			logger.debug("Invalid data for Generic Template updating");
			throw new UnprocessablEHandler("Invalid data for Generic Template updating");
		}

	}

	// Delete an existing Template
	public ResponseEntity<Void> deleteTemplate(Long templateId) {
		BotGTemplateMessage template = gTemplateRepo.findByIsDeletedFalseAndTemplateId(templateId);
		if (template != null) {
			template.setIsDeleted(true);
			template.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			template.getBotTemplateElements().forEach(element ->{
				element.setIsDeleted(true);
				element.setDeletedDate(new Timestamp(System.currentTimeMillis()));
				element.getBotButtons().forEach( button -> {
					button.setIsDeleted(true);
					button.setDeletedDate(new Timestamp(System.currentTimeMillis()));
				});
			});
			logger.debug("Delete Template which its id is " + templateId);
			gTemplateRepo.save(template);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.debug("No Template found by this id");
			throw new NotFoundHandler("No Template found by this id : "+templateId);
		}

	}

	private boolean checkTemlateValues(BotGTemplateMessage gTemplate) {
		boolean valid = false;
		if (gTemplate != null && msgRepo.findByIsDeletedFalseAndMessageId(gTemplate.getMessageId()) != null
				&& !gTemplate.getBotTemplateElements().isEmpty() && gTemplate.getBotTemplateElements().size() < 10) {
			valid = true;
		}
		return valid;
	}

	private boolean checkButtonRequiredValues(BotButton button) {
		boolean valid = false;
		if (button.getBotText().getArabicText().length() > 0
				&& (button.getButtonType().getButtonName() == "postback" && button.getButtonPayload().length() > 0)
				&& (button.getButtonType().getButtonName() == "url" && button.getButtonUrl().length() > 0)) {
			valid = true;

		}
		return valid;

	}

	private boolean checkElementRequiredValues(BotTemplateElement element) {
		boolean valid = false;
		if (element.getTitle().getArabicText().length() > 0 && element.getSubTitle().getArabicText().length() > 0
				&& element.getBotButtons().size() < 3 && !element.getBotButtons().isEmpty()) {
			valid = true;
		}
		return valid;
	}

}
