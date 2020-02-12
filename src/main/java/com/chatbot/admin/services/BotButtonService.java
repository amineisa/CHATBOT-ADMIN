package com.chatbot.admin.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.BotButton;
import com.chatbot.admin.entities.BotButtonTemplateMSG;
import com.chatbot.admin.entities.BotQuickReplyMessage;
import com.chatbot.admin.entities.BotTemplateElement;
import com.chatbot.admin.entities.ButtonType;
import com.chatbot.admin.entities.Template;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotButtonRepo;
import com.chatbot.admin.repositories.BotElementRepo;
import com.chatbot.admin.repositories.ButtonTemplateRepo;
import com.chatbot.admin.repositories.ButtonTypeRepo;
import com.chatbot.admin.repositories.QuickReplyTemplateRepo;

@Service
public class BotButtonService {

	@Autowired
	private BotButtonRepo botButtonRepo;
	@Autowired
	private ButtonTypeRepo buttonTypeRepo;
	@Autowired
	private BotElementRepo elementRepo;
	@Autowired
	private QuickReplyTemplateRepo quickRepo;
	@Autowired
	private ButtonTemplateRepo btntemplateRepo;
	

	private static final Logger logger = LoggerFactory.getLogger(BotButtonService.class);
	
	// Retrieve All Buttons
	public ResponseEntity<List<BotButton>> getAllButtons() {
		List<BotButton> buttons = botButtonRepo.findAllByIsDeletedFalse();
		List<BotButton> body = new ArrayList<>(); 
		buttons.forEach(button ->{
			setTempletId(button);
			body.add(button);
		});
		logger.debug("Get list of all objects "+BotButtonService.class);
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	private void setTempletId(BotButton button) {
		button.setButtonTemplateId(button.getBotButtonTemplateMSG() == null ? 0 : button.getBotButtonTemplateMSG().getButtonTempMsgId());
		button.setElementId(button.getBotTemplateElement() == null ? 0 : button.getBotTemplateElement().getElementId());
		button.setQuickReplyId(button.getBotQuickReplyMessage() == null ? 0 : button.getBotQuickReplyMessage().getQuickMsgId());
	}

	// Retrieve specific Button
	public ResponseEntity<BotButton> getButtonById(Long id) {
			Optional<BotButton> optionalButton = botButtonRepo.findByIsDeletedFalseAndButtonId(id);
			if (optionalButton.isPresent()) {
				BotButton button = optionalButton.get();
				setTempletId(button);
				logger.debug("Retrieve Button {} : "+button.toString());
				return new ResponseEntity<>(button, HttpStatus.OK);
			} else {
				logger.debug("No Button existing with this id : "+id);
				throw new NotFoundHandler("No Button existing with this id : "+id);
			}
	}

	// Insert new Text Object
	public ResponseEntity<BotButton> saveButton(BotButton button) {
		if(checkButtonValidity(button) && buttonTypeRepo.existsByIsDeletedFalseAndId(button.getButtonType().getId())) {
			Template template = getButtonParent(button);
			if(template != null ) {
				setButtonParent(button,template);
				button.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				button.setIsDeleted(false);
				botButtonRepo.save(button);
				return new ResponseEntity<>(button, HttpStatus.CREATED);
			}else {
				logger.warn("No button template or element or quick reply found");
				throw new NotFoundHandler("No template found by ths id ");
			}
		}else {
			logger.warn("Insert new button  Null value not accepted");
			throw new UnprocessablEHandler("Check required values ");
		}
	}

	private void setButtonParent(BotButton button,Template template) {
		if(template instanceof BotButtonTemplateMSG ) {
			BotButtonTemplateMSG buttonTemplate = (BotButtonTemplateMSG) template;
			button.setBotButtonTemplateMSG(buttonTemplate);
		}else if(template instanceof BotTemplateElement) {
			BotTemplateElement element = (BotTemplateElement) template;
			button.setBotTemplateElement(element);
		}else if(template instanceof BotQuickReplyMessage) {
			BotQuickReplyMessage quickReplyMessage = (BotQuickReplyMessage) template;
			button.setBotQuickReplyMessage(quickReplyMessage);
		}
	}

	private Template getButtonParent(BotButton button) {
		Long parentId = 0l;
		Template template = null;
		if(button.getButtonTemplateId() != 0) {
			parentId = button.getButtonTemplateId();
			template = btntemplateRepo.findByIsDeletedFalseAndButtonTempMsgId(parentId);
		}else if(button.getQuickReplyId() != 0) {
			parentId = button.getQuickReplyId();
			template = quickRepo.findByIsDeletedFalseAndQuickMsgId(parentId);
		}else if(button.getElementId() != 0) {
			parentId = button.getElementId();
			template = elementRepo.findByIsDeletedFalseAndElementId(parentId);
		}
		return template;		
	}

	private boolean checkButtonValidity(BotButton button) {
		boolean valid = true;
		if(button.getBotText().checkEmpty() || button.checkEmpty() || 
			button.getButtonType().checkEmpty()) {
			valid = false;
		}
		return valid;
	}

	// Update an existing button
	public ResponseEntity<BotButton> updateButton(BotButton button) {
		if(checkButtonValidity(button) && button.getButtonId() != null ) {
			Optional<BotButton> optionalButton = botButtonRepo.findByIsDeletedFalseAndButtonId(button.getButtonId());
			Template template = getButtonParent(button);
			if(optionalButton.isPresent() && template != null) {
				BotButton existingButton = optionalButton.get();
					setButtonParent(existingButton, template);
					existingButton.setBotText(button.getBotText());
					existingButton.setButtonPayload(button.getButtonPayload());
					existingButton.setButtonType(button.getButtonType());
					botButtonRepo.save(existingButton);
					logger.debug("Saving button "+button.toString());
					return new ResponseEntity<>(existingButton, HttpStatus.ACCEPTED);
			}else {
				logger.warn("No button template or element or quick reply found");
				throw new NotFoundHandler("No template found by ths id ");
			}
		}else {
			logger.warn("Insert new button  Null value not accepted");
			throw new UnprocessablEHandler("Check required values ");
		}
	}

	// delete an existing button object
	public ResponseEntity<Void> deleteButton(Long buttonId) {
			boolean isExist = botButtonRepo.existsById(buttonId);
			if (isExist) {
				logger.debug("Delete button  object which  its id " + buttonId);
				botButtonRepo.deleteById(buttonId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				logger.debug("you try to delete an unexisting button object ");
				throw new NotFoundHandler("you try to delete an unexisting button object "+buttonId);
			}
	}

	// retrieve buttons by its type
	public ResponseEntity<List<BotButton>> getButtonsByType(Long typeId) {
		Optional<ButtonType> optionalType = buttonTypeRepo.findById(typeId);
		if (optionalType.isPresent()) {
			List<BotButton> buttons = botButtonRepo.findAllByIsDeletedFalseAndButtonType(optionalType.get());
			List<BotButton> body = new ArrayList<>();
			buttons.forEach(button ->{
				setTempletId(button);
				body.add(button);
			});
			logger.debug("Retrieve all buttons which thier type is " + optionalType.get().getButtonName());
			return new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			logger.debug("You try to retrieve buttons by un existing type ");
			throw new NotFoundHandler("You try to retrieve buttons by un existing type ");
		}

	}

	// Retrieve buttons by element id
	public ResponseEntity<List<BotButton>> getButtonsByElement(Long elementId) {
		BotTemplateElement element = elementRepo.findByIsDeletedFalseAndElementId(elementId);
		List<BotButton> body = new ArrayList<>();
		if (element != null ) {
			logger.debug("All Buttons which by element id " + elementId);
			List<BotButton> buttons = botButtonRepo.findAllByIsDeletedFalseAndBotTemplateElement(element);
			buttons.forEach(button ->{
				setTempletId(button);
				body.add(button);
			});
			logger.debug("Retrieve buttons by element id "+elementId);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			logger.warn("No element found " + elementId);
			throw new NotFoundHandler("No element found " + elementId);
		}
	}

	// Retrieve buttons by QuickReply template id
	public ResponseEntity<List<BotButton>> getButtonsByQuickReply(Long quickreplyId) {
		BotQuickReplyMessage quickReply = quickRepo.findByIsDeletedFalseAndQuickMsgId(quickreplyId);
		if (quickReply != null) {
			List<BotButton> body = new ArrayList<>();
			logger.debug("Buttons List of quick reply " + quickreplyId);
			List<BotButton> buttons = botButtonRepo.findAllByIsDeletedFalseAndBotQuickReplyMessage(quickReply);
			buttons.forEach(button ->{
				setTempletId(button);
				body.add(button);
			});
			return new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			logger.warn("No QuickReply Message Found " + quickreplyId);
			throw new NotFoundHandler("No QuickReply Message Found " + quickreplyId);
		}
	}

	
	// Retrieve buttons by Button template id
		public ResponseEntity<List<BotButton>> getButtonsByButtonTemplate(Long btnTemplateId) {
			BotButtonTemplateMSG btnTemplate = btntemplateRepo.findByIsDeletedFalseAndButtonTempMsgId(btnTemplateId);
			if(btnTemplate != null) {
				List<BotButton> body = new ArrayList<>();
				logger.debug("Buttons List of Button Templat "+btnTemplateId);
				List<BotButton> buttons = botButtonRepo.findAllByIsDeletedFalseAndBotButtonTemplateMSG(btnTemplate);
				buttons.forEach(button ->{
					setTempletId(button);
					body.add(button);
				});
				return new ResponseEntity<>(body, HttpStatus.OK);
			}else {
				logger.warn("No Button Template found by this id "+btnTemplateId);
				throw new NotFoundHandler("No Button Template found by this id "+btnTemplateId);
			}
		}
	

}
