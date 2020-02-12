package com.chatbot.admin.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.admin.entities.BotText;
import com.chatbot.admin.services.BotTextService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/texts")
@CrossOrigin(origins = "http://localhost:4200")
@Api(value="Text Controller ",description="Text API operations ")
public class BotTextController {

	@Autowired
	BotTextService botTextService;

	@ApiOperation(value="Get All Texts ",response=ResponseEntity.class)
	@GetMapping(produces="application/json")
	public ResponseEntity<List<BotText>> getAllTexts() {
		return botTextService.getAllTexts();
	}

	@ApiOperation(value="Get specific text by its id " , response=ResponseEntity.class)
	@GetMapping(value="/{id}" , produces="application/json")
	public ResponseEntity<BotText> getTextById(@ApiParam(value="Text id ",required=true) @Valid @PathVariable Long id) {
		return botTextService.getTextByID(id);
	}
	
	@ApiOperation(value="Insert new text ",response=ResponseEntity.class)
	@PostMapping(produces="application/json")
	public ResponseEntity<BotText> saveText(@ApiParam(value="New Text object to be saved ",required=true )@Valid @RequestBody BotText text) {
		return botTextService.insertNewText(text);
	}

	/*@ApiOperation(value="Delete an existing text ",response=ResponseEntity.class)
	@DeleteMapping(value="/{id}" , produces="application/json")
	public ResponseEntity<Void> deleteText(@ApiParam(value="Text id for deleting ",required=true )@Valid @PathVariable Long id){
		return botTextService.deleteAnExistText(id);
	}*/

	@ApiOperation(value="Update an existing text ",response=ResponseEntity.class)
	@PutMapping(produces="application/json")
	public ResponseEntity<BotText> updateText(@ApiParam(value="Text object with new value to be updated ",required=true) @Valid @RequestBody BotText text){
		return botTextService.updateAnExistingText(text);
	}
	
	
}
