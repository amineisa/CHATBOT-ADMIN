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

import com.chatbot.admin.entities.BotTextResponseMapping;
import com.chatbot.admin.services.TextResponseMappingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@RequestMapping("/responsemappings")
@Api(value="Response mapping controller " , description="Response mapping API all operations")
public class TextResponseMappingController {

	@Autowired
	TextResponseMappingService mappingService;

	@GetMapping(value="application/json")
	@ApiOperation(value="Get all response mapping rows ",response=ResponseEntity.class)
	public ResponseEntity<List<BotTextResponseMapping>> allMapping() {
		return mappingService.getAllTextResponseMapping();

	}

	@GetMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Get specific response mapping row by its id ", response=ResponseEntity.class)
	public ResponseEntity<BotTextResponseMapping> mappingById(@ApiParam(value="Response Mapping id ",required=true) @Valid @PathVariable Long id ){
		return mappingService.getTextResponseMapping(id);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new response mapping row ", response=ResponseEntity.class)
	public ResponseEntity<BotTextResponseMapping> saveMapping(@ApiParam(value="New response mapping object to be saved ",required=true) @Valid @RequestBody BotTextResponseMapping mapping){
		return mappingService.insertNewMapping(mapping);
	}
	
	@PutMapping(value="application/json")
	@ApiOperation(value="Update an existing response mapping row ", response=ResponseEntity.class)
	public ResponseEntity<BotTextResponseMapping> updateMapping(@ApiParam(value="An existing Mapping with new values to be updated ",required=true) @Valid @RequestBody BotTextResponseMapping mapping){
		return mappingService.updateMapping(mapping);
	}
	
	@DeleteMapping(value="/{id}" , produces="application/json")
	@ApiOperation(value="Delete specific response mapping row by its id ", response=ResponseEntity.class)
	public ResponseEntity<Void> deleteMapping(@ApiParam(value="Response Mapping id ",required=true) @Valid @PathVariable Long id ){
		return mappingService.deleteExistingMapping(id);
	}
}
