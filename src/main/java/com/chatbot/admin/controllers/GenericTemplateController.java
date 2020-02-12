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

import com.chatbot.admin.entities.BotGTemplateMessage;
import com.chatbot.admin.services.GenericTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@RequestMapping("/generictemplates")
@Api(value="Generic template controller ",description="Generic template API operations ")
public class GenericTemplateController {

	@Autowired
	private GenericTemplateService gTemplateService;
	
	@GetMapping
	@ApiOperation(value="Get all generic templates ",response=ResponseEntity.class)
	public ResponseEntity<List<BotGTemplateMessage>> getAllTemplates(){
		return gTemplateService.getAllTemplates();
	}
	
	@GetMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Get specific generic template by id ",response=ResponseEntity.class)
	public ResponseEntity<BotGTemplateMessage> getTemplateById(@ApiParam(value="generic template id ",required=true)@Valid @PathVariable Long id ){
		return gTemplateService.getTemplateById(id);
	}
	
	@GetMapping(value="/messages/{msgId}" , produces="application/json")
	@ApiOperation(value="Get specific generic template by message id ",response=ResponseEntity.class)
	public ResponseEntity<BotGTemplateMessage> getTemplateByMsgId(@ApiParam(value="Message id ",required=true)@Valid @PathVariable Long msgId){
		return gTemplateService.getTemplateByMsgID(msgId);
	}
	
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new generic template ",response=ResponseEntity.class)
	public ResponseEntity<BotGTemplateMessage> saveTemplate(@ApiParam(value="New generic template to be saved ",required=true)@Valid @RequestBody BotGTemplateMessage gTemplate){
		return gTemplateService.insertNewTemplate(gTemplate);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Insert new generic template ",response=ResponseEntity.class)
	public ResponseEntity<BotGTemplateMessage> updateTemplate(@ApiParam(value="Existing generic template with new value to be updated ",required=true) @Valid @RequestBody BotGTemplateMessage gTemplate){
		return gTemplateService.updateTemplate(gTemplate);
	}
	
	@DeleteMapping(value="/{templateId}" , produces="application/json")
	@ApiOperation(value="Delete an existing generic template ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteTemplate(@ApiParam(required=true ,value="Generic template id ") @Valid @PathVariable Long templateId){
		return gTemplateService.deleteTemplate(templateId);
	}
}
