package com.chatbot.admin.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.services.InteractionMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:4200")
@Api(value="Interaction's  messages controller",description="Interaction Messages API Operations")
public class InteractionMessageController {
	
	@Autowired
	InteractionMessageService messageService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get all messages ",response=ResponseEntity.class)
	ResponseEntity<List<InteractionMessage>>retrieveAllMessages(){
		return messageService.getAllMessages();
	}

	@GetMapping(value="/interactions/{interactionId}" , produces="application/json")
	@ApiOperation(value="Get List of messages which belong to specific interaction by interaction id" , response=ResponseEntity.class)
	ResponseEntity<List<InteractionMessage>>retrieveAllMessagesByInteractionId(@ApiParam(value="Interaction id ",required=true)@Valid @PathVariable Long interactionId){
		return messageService.getAllMessagesByInteractionId(interactionId);
	}
	
	@GetMapping(value="/{messageId}" ,produces="application/json")
	@ApiOperation(value="Get specific message with id ",response=ResponseEntity.class)
	ResponseEntity<InteractionMessage> retrieveMessageById(@ApiParam(value="Message id ",required=true)@Valid @PathVariable Long messageId){
		return messageService.getMessageByItsId(messageId);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new interaction message ",response=ResponseEntity.class)
	ResponseEntity<InteractionMessage> insertNewMessage(@ApiParam(value="New interaction message object to be saved ",required=true)@Valid @RequestBody InteractionMessage message){
		return messageService.insertNewMessage(message);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Update an existing message ",response=ResponseEntity.class)
	ResponseEntity<InteractionMessage> updateAnExistingMessage(@ApiParam(value="An existing message with new values to be updated " ,required=true) @Valid @RequestBody InteractionMessage message){
		return messageService.updateExistMessage(message);
	}
	
	@DeleteMapping(value="/{messageId}" , produces="application/json")
	@ApiOperation(value="Delete an existing message" , response=ResponseEntity.class)
	ResponseEntity<Void> deleteAnExistMessage(@ApiParam(required=true,value="Message id ")@PathVariable Long messageId){
		return messageService.deleteMessage(messageId);
	}
	
	
	
	
}
