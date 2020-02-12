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

import com.chatbot.admin.entities.WebServiceResponseType;
import com.chatbot.admin.services.WebServiceResponseTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@RequestMapping("/wsresponsetypes")
@Api(value="Webservice Response Type Controller ", description="Webservice Response Type API all operations")
public class WebServiceResponseTypeController {

	@Autowired
	private WebServiceResponseTypeService wsService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get all response types ",response=ResponseEntity.class)
	public ResponseEntity<List<WebServiceResponseType>> getAllTypes(){
		return wsService.getAllTypes();
	}
	
	@GetMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Get specific response type by its id ",response=ResponseEntity.class)
	public ResponseEntity<WebServiceResponseType> getTypeById(@ApiParam(required=true , value="Response type id ") @Valid @PathVariable Long id){
		return wsService.getSpecificTypeById(id);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new reponse type ",response=ResponseEntity.class)
	public ResponseEntity<WebServiceResponseType> saveType(@ApiParam(required=true , value="New Response type to be saved ") @Valid @RequestBody WebServiceResponseType type){
		return wsService.insertNewWsType(type);
	}
	
	@PutMapping(produces="applicaion/json")
	@ApiOperation(value="Update existing response type ",response=ResponseEntity.class)
	public ResponseEntity<WebServiceResponseType> updateType(@ApiParam(required=true , value="Existing type object with new values to be updated ") @Valid @RequestBody WebServiceResponseType type){
		return wsService.updateExistWsType(type);
	}
	
	@DeleteMapping(value="/{id}" , produces="applicaion/json")
	@ApiOperation(value="Delete existing response type ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteType(@ApiParam(required=true , value="Response type id ") @Valid @PathVariable Long id){
		return wsService.deleteExistingType(id);
	}
	
	
}
