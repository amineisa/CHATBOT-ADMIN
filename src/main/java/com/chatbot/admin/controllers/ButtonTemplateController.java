package com.chatbot.admin.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.admin.entities.BotButtonTemplateMSG;
import com.chatbot.admin.services.ButtonTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/buttontemplates")
@Api(value="Button Template Controllers ",description="All button template API operations")
public class ButtonTemplateController {

	@Autowired
	private ButtonTemplateService buttonTemplateService;

	
	@GetMapping
	@ApiOperation(value="Get all existing button templates ",response=ResponseEntity.class)
	public ResponseEntity<List<BotButtonTemplateMSG>> getAllButtonsTemplate() {
		return buttonTemplateService.getAllButtonTemplate();
	}

	@GetMapping("/{id}")
	@ApiOperation(value="Get specific button template by its id ",response=ResponseEntity.class)
	public ResponseEntity<BotButtonTemplateMSG> getTemplateById(@ApiParam(value="Button template id " ,required=true )@Valid @PathVariable Long id) {
		return buttonTemplateService.getButtonTemplateById(id);
	}
	
	@GetMapping("messages/{msgId}")
	@ApiOperation(value="Get button template by message id ",response=ResponseEntity.class)
	public ResponseEntity<BotButtonTemplateMSG> getTemplateByMsgId(@ApiParam(value="Message id",required=true)@PathVariable Long msgId){
		return buttonTemplateService.getButtonTemplateByMsgId(msgId);
	}
	

	
	@PostMapping
	@ApiOperation(value="Insert new button template ",response=ResponseEntity.class)
	public ResponseEntity<BotButtonTemplateMSG> saveButtonTemplate(@ApiParam(value="new button template Object to be saved ",required=true) @Valid @RequestBody BotButtonTemplateMSG buttonTemplate) {
		return buttonTemplateService.insertNewTemplate(buttonTemplate);
	}

	@PutMapping
	@ApiOperation(value="Update an existing button template ",response=ResponseEntity.class)
	public ResponseEntity<BotButtonTemplateMSG> updateTemplate(@ApiParam(required=true ,value="Button template with new value to be updated ")@Valid @RequestBody BotButtonTemplateMSG buttonTemplate){
		return buttonTemplateService.updateExistingTemplate(buttonTemplate);
	}
	
	@DeleteMapping("/{templateId}")
	@ApiOperation(value="Delete an existing button template ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteButtonTemplate(@PathVariable Long templateId){
		return buttonTemplateService.deleteExistingTemplate(templateId);
	}
}
