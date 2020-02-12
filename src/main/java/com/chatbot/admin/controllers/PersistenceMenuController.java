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

import com.chatbot.admin.entities.PersistenceMenuButton;
import com.chatbot.admin.services.PersistenceMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@RequestMapping("/persistencemenu")
@Api(value="Persistence Menu Controller",description="Persistence menu's buttons API all operations")
public class PersistenceMenuController {

	@Autowired
	private PersistenceMenuService persistenceMenuService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get all persistence menu's buttons " , response=ResponseEntity.class)
	public ResponseEntity<List<PersistenceMenuButton>> allButtons(){
		return persistenceMenuService.getAllButtons();
	}
	
	@GetMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Get specific persistence menu's button by its id " , response=ResponseEntity.class)
	public ResponseEntity<PersistenceMenuButton> oneButtonById(@ApiParam(required=true,value="Persistence Button id ") @Valid @PathVariable Long id ){
		return persistenceMenuService.getOneButtonById(id);
	}
	@GetMapping(value="/parents/{id}" , produces="application/json")
	@ApiOperation(value="Get list of  persistence menu's buttons which belongs to nested button by parent id " , response=ResponseEntity.class)
	public ResponseEntity<List<PersistenceMenuButton>> allButtonsByParentId(@ApiParam(required=true,value="Nested Persistence Button id ") @Valid @PathVariable Long id){
		return persistenceMenuService.getAllByParentId(id);
	}
	
	
	@PostMapping(value="application/json")
	@ApiOperation(value="Insert new persistence menu button ",response=ResponseEntity.class)
	public ResponseEntity<PersistenceMenuButton> saveButton(@ApiParam(required=true , value="New persistence menu button object to be saved ") @Valid @RequestBody PersistenceMenuButton button){
		return persistenceMenuService.insertNewButton(button);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Update existing persistence menu button ",response=ResponseEntity.class)
	public ResponseEntity<PersistenceMenuButton> updateButtton(@ApiParam(required=true , value="Existing persistence menu button object with new values to be updated ") @Valid @RequestBody PersistenceMenuButton button){
		return persistenceMenuService.updateExistingButton(button);
	}
	
	@DeleteMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Delete an existing persistence menu button",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteButton(@ApiParam(required=true , value="Persistence menu button id ")@PathVariable Long id ){
		return persistenceMenuService.deleteExistButton(id);
	}
	
	
}
