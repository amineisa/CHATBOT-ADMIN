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

import com.chatbot.admin.entities.ButtonType;
import com.chatbot.admin.services.ButtonTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
@RequestMapping("/buttontypes")
@Api(value="Button type controller" , description="All button type operations ")
public class ButtonTypeController {

	@Autowired
	private ButtonTypeService buttonTypeService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get all button types ",response=ResponseEntity.class)
	public ResponseEntity<List<ButtonType>> getAllTypes(){
		return buttonTypeService.getAlltypes();
	}
	
	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Get specific button type by id  ",response=ResponseEntity.class)
	public ResponseEntity<ButtonType> getTypeById(@ApiParam(value="Button type id ",required=true )@Valid @PathVariable Long id ){
		return buttonTypeService.getButtonTypeById(id);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new button type ",response=ResponseEntity.class)
	public ResponseEntity<ButtonType> saveType(@ApiParam(value="New button type object to be saved ",required=true)@Valid @RequestBody ButtonType type){
		return buttonTypeService.insertNewType(type);
	}
	
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Update an existing button type ",response=ResponseEntity.class)
	public ResponseEntity<ButtonType> updateType(@ApiParam(value="Button type object with new value to be updated ",required=true)@Valid @RequestBody ButtonType type){ 
		return buttonTypeService.updateExistingType(type);
	}
	
	@DeleteMapping(value="/{typeId}" , produces="application/json")
	@ApiOperation(value="Delete specific button type ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteType(@ApiParam(required=true, value="Button type id ")@Valid @PathVariable Long typeId){
		return buttonTypeService.deleteExistingType(typeId);
	}
}
