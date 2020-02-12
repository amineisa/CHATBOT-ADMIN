package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotButtonTemplateMSG;
import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotButtonRepo;
import com.chatbot.admin.repositories.BotInteractionMessageRepo;
import com.chatbot.admin.repositories.ButtonTemplateRepo;
import com.chatbot.admin.repositories.ButtonTypeRepo;

@Service
public class ButtonTemplateService {

	@Autowired
	private ButtonTemplateRepo buttonTemplateRepo;
	@Autowired
	private BotInteractionMessageRepo messageRepo;
	@Autowired
	private BotButtonRepo buttonRepo;
	@Autowired
	private ButtonTypeRepo buttonTypeRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(ButtonTemplateService.class);

	// get all ButtonTemplates
	public ResponseEntity<List<BotButtonTemplateMSG>> getAllButtonTemplate() {
		List<BotButtonTemplateMSG> body = buttonTemplateRepo.findAllBotButtonTemplateMSGByIsDeletedFalse();
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	// retrieve button template by its id
	public ResponseEntity<BotButtonTemplateMSG> getButtonTemplateById(Long id) {
		BotButtonTemplateMSG template = buttonTemplateRepo.findByIsDeletedFalseAndButtonTempMsgId(id);
		if (template != null) {
			logger.debug("Retrieve button template " + template.toString());
			return new ResponseEntity<>(template, HttpStatus.OK);
		} else {
			logger.debug("you try to retrieve an unexisting template ");
			throw new NotFoundHandler("No template existing with this id "+id);
		}
	}

	// retrieve ButtonTemplate by message id
	public ResponseEntity<BotButtonTemplateMSG> getButtonTemplateByMsgId(Long msgId) {
		InteractionMessage msg = messageRepo.findByIsDeletedFalseAndMessageId(msgId);
		if (msg != null && buttonTemplateRepo.findBotButtonTemplateMSGByIsDeletedFalseAndInteractionMessage(msg) != null) {
			logger.debug("Retrieve Button Template which belongs to Message " + msg.toString());
			BotButtonTemplateMSG body = buttonTemplateRepo
					.findBotButtonTemplateMSGByIsDeletedFalseAndInteractionMessage(msg);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			logger.debug("No message found with this id null values");
			throw new NotFoundHandler("No message found with this id null values");
		}
	}

	// insert new button Template
	public ResponseEntity<BotButtonTemplateMSG> insertNewTemplate(BotButtonTemplateMSG btnTemplate) {
		if (checkValidity(btnTemplate)) {
			btnTemplate.getButtons().forEach(button ->{
				button.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				button.setBotButtonTemplateMSG(btnTemplate);
				button.setIsDeleted(false);
			});
			btnTemplate.setIsDeleted(false);
			btnTemplate.setInteractionMessage(messageRepo.findByIsDeletedFalseAndMessageId(btnTemplate.getMessageId()));
			btnTemplate.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			BotButtonTemplateMSG buttonTemplate = buttonTemplateRepo.save(btnTemplate);
			return new ResponseEntity<>(buttonTemplate, HttpStatus.CREATED);
		} else {
			logger.debug("you can't insert null values");
			throw new UnprocessablEHandler("Check these values Null text or No message existing with this id or buttons more than 4 buttons or buttons less than 1 button ");
		}
	}

	// Update an Existing Template
	public ResponseEntity<BotButtonTemplateMSG> updateExistingTemplate(BotButtonTemplateMSG buttonTemplate) {
		if (checkValidity(buttonTemplate) && buttonTemplateRepo.existsById(buttonTemplate.getButtonTempMsgId())) {
			InteractionMessage message = messageRepo.findByIsDeletedFalseAndMessageId(buttonTemplate.getMessageId());	
			logger.debug("Retrieve button template which its id is " + buttonTemplate.getButtonTempMsgId());
				BotButtonTemplateMSG existingTemplate = buttonTemplateRepo.findByIsDeletedFalseAndButtonTempMsgId(buttonTemplate.getButtonTempMsgId());
				buttonTemplate.getButtons().forEach(button ->{
					Timestamp creationDate = button.getButtonId()!= null ? buttonRepo.findByIsDeletedFalseAndButtonId(button.getButtonId()).get().getCreatedDate() : new Timestamp(System.currentTimeMillis());
					button.setCreatedDate(creationDate);
					button.setBotButtonTemplateMSG(existingTemplate);
					button.setIsDeleted(false);
				});
				existingTemplate.setButtonTempMsgId(buttonTemplate.getButtonTempMsgId());
				existingTemplate.setInteractionMessage(message);
				existingTemplate.setBotText(buttonTemplate.getBotText());
				existingTemplate.setButtons(buttonTemplate.getButtons());
				existingTemplate.setIsStatic(buttonTemplate.getIsStatic());
				existingTemplate.setIsDeleted(false);
				existingTemplate.setCreatedDate(existingTemplate.getCreatedDate());
				return new ResponseEntity<>(buttonTemplateRepo.save(existingTemplate), HttpStatus.ACCEPTED);
		} else {
			logger.debug("Invalid button template");
			throw new UnprocessablEHandler("Check these values Null text or No message existing with this id or buttons more than 4 buttons or buttons less than 1 button ");
		}
	}

	// Delete an Existing Template by its id
	public ResponseEntity<Void> deleteExistingTemplate(Long templateId) {
		BotButtonTemplateMSG template = buttonTemplateRepo.findByIsDeletedFalseAndButtonTempMsgId(templateId);
		if (template != null) {
			logger.debug("Delete Button Template  " + templateId);
			//List<BotButton> buttons = new ArrayList<>();
			template.getButtons().forEach(button ->{
				button.setIsDeleted(true);
				button.setDeletedDate(new Timestamp(System.currentTimeMillis()));
				//button.setBotButtonTemplateMSG(null);
				//buttons.add(button);
				buttonRepo.save(button);
			});
			template.setIsDeleted(true);
			template.setCreatedDate(template.getCreatedDate());
			template.setDeletedDate(new Timestamp(System.currentTimeMillis()));
			//template.setButtons(buttons);
			buttonTemplateRepo.save(template);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.debug("Delete Button template : No template found by this id");
			throw new NotFoundHandler("Delete Button template : No template found by this id");
		}
	}
	
	
	private boolean checkValidity(BotButtonTemplateMSG btnTemplate) {
		boolean valid = false;
		if(btnTemplate != null &&  messageRepo.findByIsDeletedFalseAndMessageId(btnTemplate.getMessageId()) != null &&
				btnTemplate.getBotText().getArabicText().length() > 0 && !btnTemplate.getButtons().isEmpty() && btnTemplate.getButtons().size() < 4) {
			valid = true;
		}
		return valid;
	}
}
