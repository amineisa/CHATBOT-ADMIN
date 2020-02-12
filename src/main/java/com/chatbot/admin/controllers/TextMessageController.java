package com.chatbot.admin.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.admin.entities.BotTextMessage;
import com.chatbot.admin.services.TextMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/textmessages")
@CrossOrigin(origins = "http://localhost:4200")
@Api(value="Text Message Controller " , description="Text Message API all operations")
public class TextMessageController {

	@Autowired
	private TextMessageService txtMsgService;
	
	
	@GetMapping
	@ApiOperation(value="Get all existing text messgaes ",response=ResponseEntity.class)
	public ResponseEntity<List<BotTextMessage>> allTextMsgs(){
		return txtMsgService.allTextMessages();
	}
	
	@GetMapping(value="/{id}",produces="application/json")
	@ApiOperation(value="Get specific Text message by its id " ,response=ResponseEntity.class) 
	public ResponseEntity<BotTextMessage> oneTxtMsgById(@ApiParam(required=true,value="Text message id ") @Valid @PathVariable Long id){
		return txtMsgService.textMsgById(id);
	}
	
	@GetMapping(value="messages/{msgId}",produces="application/json")
	@ApiOperation(value="Get text message by message id " , response=ResponseEntity.class)
	public ResponseEntity<BotTextMessage> oneTxtMsgByMessagId(@ApiParam(value="Interaction message id ",required=true)@PathVariable Long msgId){
		return txtMsgService.textMsgByMessageId(msgId);
	}
	
	@PostMapping(produces="application/json")
	public ResponseEntity<BotTextMessage> savetxtMsg(@ApiParam(required=true ,value="New Text message object to be saved ") @Valid @RequestBody BotTextMessage textMsg){
		return txtMsgService.insertNewTextMessage(textMsg);
	}
	
	@PutMapping(produces="application/json")
	@ApiOperation(value="Update an existing text messgae ",response=ResponseEntity.class)
	public ResponseEntity<BotTextMessage> updateTxtMsg(@ApiParam(required=true ,value="Existing text message object with new values to be updated ") @Valid @RequestBody BotTextMessage txtMsg){
		return txtMsgService.updateExistingTextMessage(txtMsg);
	}
	
	@DeleteMapping(value="/{txtMsgId}",produces="application/json")
	@ApiOperation(value="Delete text message ",response=ResponseEntity.class)
	public ResponseEntity<Void> deleteTxtMsg(@ApiParam(required=true,value="Text message id ") @Valid @PathVariable Long txtMsgId){
		return txtMsgService.deleteExistingTxtMsg(txtMsgId);
	}
}
