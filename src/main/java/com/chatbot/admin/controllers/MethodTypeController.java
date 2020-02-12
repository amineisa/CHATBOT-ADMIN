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

import com.chatbot.admin.entities.BotMethodType;
import com.chatbot.admin.services.MethodTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/methodtypes")
@Api(value="Http methoud type controller",description="All webservice calling methods types API Operation")
public class MethodTypeController {

	@Autowired
	MethodTypeService typeService;

	@GetMapping(produces = "application/json")
	@ApiOperation(value="Get all http methods types",response=ResponseEntity.class)
	public ResponseEntity<List<BotMethodType>> getAllTypes() {
		return typeService.getAllMethodTypes();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value="Get specific method type by id ",response=ResponseEntity.class)
	public ResponseEntity<BotMethodType> getOneType(@ApiParam(required=true,value="Method type id ")@Valid @PathVariable Long id) {
		return typeService.getMethodTypeById(id);
	}

	@PostMapping(produces = "application/json")
	@ApiOperation(value="Insert new method type ",response=ResponseEntity.class)
	public ResponseEntity<BotMethodType> saveType(@ApiParam(required=true,value="New method type object to be saved ")@Valid@RequestBody BotMethodType type) {
		return typeService.insertType(type);
	}

	@PutMapping(produces = "application/json")
	@ApiOperation(value="Update an existing type ",response=ResponseEntity.class)
	public ResponseEntity<BotMethodType> updateType(@ApiParam(required=true,value="An existing method type with new values to be updated ")@Valid@RequestBody BotMethodType type) {
		return typeService.updateExistingType(type);
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value="Delete an existing method type ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteType(@ApiParam(required=true,value="Method type id ")@Valid@PathVariable Long id) {
		return typeService.deleteMessageType(id);
	}

}
