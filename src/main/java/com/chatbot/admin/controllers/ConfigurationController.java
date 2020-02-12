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

import com.chatbot.admin.entities.BotConfiguration;
import com.chatbot.admin.services.ConfigurationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController()
@RequestMapping("/configuratios")
@Api(value="Confguration Controller ",description="Configuration API operations")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService; 
	
	
	@GetMapping()
	@ApiOperation(value="Get all configuration rows  ",response=ResponseEntity.class)
	public ResponseEntity<List<BotConfiguration>> allRows(){
		return configurationService.getAllRows();
	}
	
	@GetMapping(value="/{id}",produces="application/json")
	@ApiOperation(value="Get specific configuration row by its id ",response=ResponseEntity.class)
	public ResponseEntity<BotConfiguration> oneRowById(@ApiParam(value="ID",required=true)@Valid @PathVariable Long id ) {
		return configurationService.getOneRow(id);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value=" Insert new configuration row ",response=ResponseEntity.class)
	public ResponseEntity<BotConfiguration> saveConfigurationRow(@ApiParam(value="new configuration object to be saved ",required=true)@RequestBody BotConfiguration configuration){
		return configurationService.insertNewRow(configuration);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="update an existing configuration row ",response=ResponseEntity.class)
	public ResponseEntity<BotConfiguration> updateConfigurationRow(@ApiParam(value="Configuration object with new value to be saved " , required=true) @Valid @RequestBody BotConfiguration configuration){
		return configurationService.updateExistigRow(configuration);
	}
	
	@DeleteMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Delete specific configuration row by its id ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteOneRow(@ApiParam(required=true,value="configuration row id ")@Valid@PathVariable Long id){
		return configurationService.deleteExistingRow(id);
	}
	
	
	
}
