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

import com.chatbot.admin.entities.BotTemplateElement;
import com.chatbot.admin.services.BotElementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@RequestMapping(value="/elements")
@Api(value="Generic template Element controller " , description="All elemnet operations ")
public class BotElementController {

	@Autowired
	private BotElementService elementService;
	
	@ApiOperation(value="Get all existing elements" , response=ResponseEntity.class)
	@GetMapping(produces="application/json")
	public ResponseEntity<List<BotTemplateElement>> getAllElements(){
		return elementService.getAllElements();
	}
	
	@ApiOperation(value="Get Specific Element by its id ",response=ResponseEntity.class)
	@GetMapping(value="{elementId}",produces="application/json" )
	public ResponseEntity<BotTemplateElement> getElementById(@ApiParam(value="Element id ",required=true) @Valid @PathVariable Long elementId){
		return elementService.getElementById(elementId);
	}
	
	@ApiOperation(value="Get List of elements which belong to specific generic template by template id" ,response=ResponseEntity.class)
	@GetMapping(value="/templates/{templateId}" ,produces="application/json")
	public ResponseEntity<List<BotTemplateElement>> getAllElementsByTemplateId(@ApiParam(value="Generic template id ",required=true)@Valid @PathVariable Long templateId){
		return elementService.getElementsByTemplateID(templateId);
	}
	@ApiOperation(value="Insert new element ",response=ResponseEntity.class)
	@PostMapping(produces="application/json")
	public ResponseEntity<BotTemplateElement> saveElement(@ApiParam(value="New element object to be saved ",required=true) @Valid @RequestBody BotTemplateElement element){
		return elementService.insertNewElement(element);
	}
	
	@ApiOperation(value="Update an existing element ",response=ResponseEntity.class)
	@PutMapping(produces="application/json")
	public ResponseEntity<BotTemplateElement> updateElment(@ApiParam(value="Element object with new value to be updated ",required=true)@Valid @RequestBody BotTemplateElement element){
		return elementService.updateElement(element);
	}

	@ApiOperation(value="Delete an existing element ", response=ResponseEntity.class)
	@DeleteMapping(value="/{elementId}" , produces="application/json")
	public ResponseEntity<Void> deleteElement(@ApiParam(value="Elmenet ID  ",required=true)@Valid @PathVariable Long elementId){
		return elementService.deleteElement(elementId);
	}


}


