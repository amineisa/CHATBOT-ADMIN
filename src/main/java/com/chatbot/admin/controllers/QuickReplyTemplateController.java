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

import com.chatbot.admin.entities.BotQuickReplyMessage;
import com.chatbot.admin.services.QuickReplyTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/quickreplies")
@Api(value="Quick Reply Template Controller ",description="Quick replies template API all operations ")
public class QuickReplyTemplateController {

	
	
	@Autowired
	QuickReplyTemplateService quickService;
	
	
	@GetMapping
	@ApiOperation(value="Get all Quick reply templates ",response=ResponseEntity.class)
	public ResponseEntity<List<BotQuickReplyMessage>> allTemplates(){
		return quickService.getAllQuickReplyTemplates();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="Get specific  Quick reply template by id  ",response=ResponseEntity.class)
	public ResponseEntity<BotQuickReplyMessage> oneTemplateById(@ApiParam(required=true ,value="Quick reply template id ") @Valid @PathVariable Long id){
		return quickService.getQuickTemplateById(id);
	}
	
	
	@GetMapping("messages/{msgId}")
	@ApiOperation(value="Get specific  Quick reply template by message id ",response=ResponseEntity.class)
	public ResponseEntity<BotQuickReplyMessage> oneTemplatebyMsgId(@ApiParam(required=true ,value="Messgae id ") @Valid @PathVariable Long msgId){
		return quickService.getQuickTemplateByMsgId(msgId);
	}
	
	
	@PostMapping
	@ApiOperation(value="Insert new  Quick reply template ",response=ResponseEntity.class)
	public ResponseEntity<BotQuickReplyMessage> savetemplate(@ApiParam(required=true ,value="New Quick reply template object to be saved ") @Valid @RequestBody BotQuickReplyMessage quickTemplate){
		return quickService.insertNewTemplte(quickTemplate);
	}
	
	@PutMapping
	@ApiOperation(value="Insert existing  Quick reply template ",response=ResponseEntity.class)
	public ResponseEntity<BotQuickReplyMessage> updateTemplate(@ApiParam(required=true ,value="Existing Quick reply template with new values to be updated ") @Valid @RequestBody BotQuickReplyMessage quickTemplate){
		return quickService.updateAnExistinfTemplate(quickTemplate);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value="Delete specific  Quick reply template by id  ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteOneTemplate(@PathVariable Long id ){
		return quickService.deleteAnExistinfTemplate(id);
	}
	
	
	
}
