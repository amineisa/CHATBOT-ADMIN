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

import com.chatbot.admin.entities.BotMessageType;
import com.chatbot.admin.services.MessageTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/messagetypes")
@Api(value="Message type Controller ", description="Messgae types API Operations")
public class MessageTypeContoller {
	
	@Autowired
	private MessageTypeService typeService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get All messages types ",response=ResponseEntity.class)
	public ResponseEntity<List<BotMessageType>> getAllTypes(){
		return typeService.getAllTypes();
	}
	
	@GetMapping(value="/{id}" ,produces="application/json")
	@ApiOperation(value="Get specific message type by id  ",response=ResponseEntity.class)
	public ResponseEntity<BotMessageType> getOneType(@ApiParam(required=true , value="Messgae type id ") @Valid @PathVariable Long id ){
		return typeService.getTypeById(id);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new  message type ",response=ResponseEntity.class)
	public ResponseEntity<BotMessageType> saveType(@ApiParam(required=true ,value="New message Type object to be saved ")@Valid @RequestBody BotMessageType type){
		return typeService.insertType(type);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Update an existing message type ",response=ResponseEntity.class)
	public ResponseEntity<BotMessageType> updateType(@ApiParam(value="An existing message type with new values to be updated ",required=true) @Valid @RequestBody BotMessageType type){
		return typeService.updateExistingType(type);
	}
	
	@DeleteMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Delete an existing message type",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteType(@ApiParam(required=true , value="Message type id ") @Valid @PathVariable Long id ){
		return typeService.deleteMessageType(id);
	}
	
	
}
