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

import com.chatbot.admin.entities.WebserviceProperties;
import com.chatbot.admin.services.WebservicePropertiesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@RequestMapping("/webservices")
@Api(value="Webservice information controller ",description="Web sevice message all operations for dynamic response ")
public class WebservicePropertiesController {

	@Autowired
	private WebservicePropertiesService wsMessageService;
	
	@GetMapping(produces="application/json")
	@ApiOperation(value="Get all webservice information rows" , response=ResponseEntity.class)
	public ResponseEntity<List<WebserviceProperties>> getAllWS(){
		return wsMessageService.getAllWebService();
	}
	
	@GetMapping(value ="/{id}" ,produces="application/json")
	@ApiOperation(value="Get specific webservice information row by its id " , response=ResponseEntity.class)
	public ResponseEntity<WebserviceProperties> getWSByid(@ApiParam(value="Webservice information row id ",required=true) @Valid @PathVariable Long id){
		return wsMessageService.getWebserviceByid(id);
	}
	
	@GetMapping(value="/messages/{msgId}",produces="application/json")
	@ApiOperation(value="Get specific webservice information row which belongs to specifc message by message id" , response=ResponseEntity.class)
	public ResponseEntity<WebserviceProperties> getWSByMsgId(@ApiParam(value="Interaction message id ",required=true) @Valid @PathVariable Long msgId){
		return wsMessageService.getwebserviceByMSGID(msgId);
	}
	
	@PostMapping(produces="application/json")
	@ApiOperation(value="Insert new webservice information row " , response=ResponseEntity.class)
	public ResponseEntity<WebserviceProperties> saveWS(@ApiParam(value="Webservice infromation object to be saved  ",required=true) @Valid @RequestBody WebserviceProperties wsMsg){
		return wsMessageService.insertNewWebservice(wsMsg);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Update existing webservice information row " , response=ResponseEntity.class)
	public ResponseEntity<WebserviceProperties> updateWS(@ApiParam(value="Webservice infromation object to be updated ",required=true) @Valid @RequestBody WebserviceProperties wsMsg){
		return wsMessageService.updateAnExistingWS(wsMsg);
	}
	
	@DeleteMapping(value="/{wsId}" , produces="application/json")
	@ApiOperation(value="Delete existing webservice information row" , response=ResponseEntity.class)
	public ResponseEntity<Void> deleteWS(@ApiParam(value="Webservice information row id",required=true) @Valid @PathVariable Long wsId){
		return wsMessageService.deleteAnExistingWS(wsId);
	}
	
	
}
