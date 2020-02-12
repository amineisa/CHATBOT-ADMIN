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

import com.chatbot.admin.entities.BotInteraction;
import com.chatbot.admin.services.InteractionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/interactions")
@Api(value="Interaction Controller ",description="Interactions API operations ")
public class InteractionController {

	@Autowired
	InteractionService interactionService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get all interactions",response=ResponseEntity.class)
	ResponseEntity<List<BotInteraction>> allInteractions(){
		return interactionService.getAllInteractions();	
	}
 	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Get specific interaction by id ",response=ResponseEntity.class)
	ResponseEntity<BotInteraction> interactionById(@ApiParam(required=true,value="Interaction id") @Valid @PathVariable Long id ) {
		return interactionService.getInteractionById(id);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new interaction",response=ResponseEntity.class)
	ResponseEntity<BotInteraction> insertInteraction(@ApiParam(required=true,value="New interaction object to be saved") @Valid @RequestBody BotInteraction interaction) {
		return interactionService.saveInteraction(interaction);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Get specific interaction by id ",response=ResponseEntity.class)
	ResponseEntity<BotInteraction> updateExistInteraction(@ApiParam(required=true , value="An existing interaction with new value to be updated") @Valid @RequestBody BotInteraction interaction) {
		return interactionService.updateInteraction(interaction);
		
	}

	@DeleteMapping(value="/{interactionId}" , produces="application/json")
	@ApiOperation(value="Delete specific interaction ", response=ResponseEntity.class)
	ResponseEntity<Void>  deleteExistInteraction(@ApiParam(required=true,value="Interaction id ") @Valid @PathVariable Long interactionId) {
		return interactionService.deleteInteraction(interactionId);
	}
}
