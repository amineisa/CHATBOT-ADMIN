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

import com.chatbot.admin.entities.EnabledCategoryConfiguration;
import com.chatbot.admin.services.EnabledCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@RequestMapping("/categories")
@Api(value="Enabled Category Controller",description="Enabled categories API operations ")
public class EnabledCategoryController {

	@Autowired
	private EnabledCategoryService enabledCategoryService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get All Enabled Categories ",response=ResponseEntity.class)
	public ResponseEntity<List<EnabledCategoryConfiguration>> allCategories(){
		return enabledCategoryService.allEnabledCategories();
	}
	
	@GetMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Get specific Enabled Category by its id ",response=ResponseEntity.class)
	public ResponseEntity<EnabledCategoryConfiguration> oneCategoryById(@ApiParam(value="Enabled category id ",required=true) @Valid @PathVariable Long id ){
		return enabledCategoryService.specificCategoryById(id);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new enabled Category ",response=ResponseEntity.class)
	public ResponseEntity<EnabledCategoryConfiguration> insertCategory(@ApiParam(required=true,value="New Enabled category object to be saved") @Valid @RequestBody EnabledCategoryConfiguration category){
		return enabledCategoryService.insertNewObject(category);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="update existing  enabled Category ",response=ResponseEntity.class)
	public ResponseEntity<EnabledCategoryConfiguration> updateCategory(@ApiParam(required=true,value="Enabled category object with new values to be updated") @Valid @RequestBody EnabledCategoryConfiguration category){
		return enabledCategoryService.insertNewObject(category);
	}
	
	@DeleteMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Delete an existing enabled Category ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteCategory(@ApiParam(value="Enabled category id ",required=true)@Valid @PathVariable Long id ){
		return enabledCategoryService.deleteCategory(id);
	}
}
