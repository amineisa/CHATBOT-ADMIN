package com.chatbot.admin.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatbot.admin.entities.InteractionMessage;
import com.chatbot.admin.entities.WebserviceProperties;
import com.chatbot.admin.exceptions.NotFoundHandler;
import com.chatbot.admin.exceptions.UnprocessablEHandler;
import com.chatbot.admin.repositories.BotInteractionMessageRepo;
import com.chatbot.admin.repositories.WebservicePropertiesRepo;

@Service
public class WebservicePropertiesService {

	@Autowired
	private WebservicePropertiesRepo webserviceMessageRepo;
	@Autowired
	private BotInteractionMessageRepo msgRepo;
	

	
	private static final Logger logger = LoggerFactory.getLogger(WebservicePropertiesService.class);
	
	//Retrieve all web service details 
	public ResponseEntity<List<WebserviceProperties>> getAllWebService(){
		List<WebserviceProperties> body = webserviceMessageRepo.findAll();
		return new ResponseEntity<>(body , HttpStatus.OK);
	}
	
	//Retrieve specific web service details by id 
	public ResponseEntity<WebserviceProperties> getWebserviceByid(Long id){
			Optional<WebserviceProperties> optionalWS = webserviceMessageRepo.findById(id);
			if(optionalWS.isPresent()){
				return new ResponseEntity<>(optionalWS.get(), HttpStatus.OK);
			}else {
				logger.debug("NO WebService found by this id ");
				throw new NotFoundHandler();
			}
			}
	
	//Retrieve specific web service by message id
	public ResponseEntity<WebserviceProperties> getwebserviceByMSGID(Long msgId){
		Optional<InteractionMessage> optinoalMsg = msgRepo.findById(msgId);
		if(optinoalMsg.isPresent()) {
			logger.debug("Get Webservice List which related to Message "+optinoalMsg.get().toString());
			WebserviceProperties body = webserviceMessageRepo.findAllByInteractionMessage(optinoalMsg.get());
			return new ResponseEntity<>(body, HttpStatus.OK);
		}else {
			logger.debug("NO Webservce found for this Message "+msgId);
			throw new NotFoundHandler();
		}
	}
	
	//Insert new web service  
	public ResponseEntity<WebserviceProperties> insertNewWebservice(WebserviceProperties webService){
		if(webService != null) {
			logger.debug("Insert New WebService Object "+webService.toString());
			webserviceMessageRepo.save(webService);
			return new ResponseEntity<>(webService, HttpStatus.CREATED);
		}else {
			logger.debug("You can not insert Invalid Null Data ");
			throw new UnprocessablEHandler();
		}
	}
	
	//Update an existing web service 
	public ResponseEntity<WebserviceProperties> updateAnExistingWS(WebserviceProperties webService ){
		if(webService != null && webService.getWsMsgId() != null) {
			Optional<WebserviceProperties> optionalWS = webserviceMessageRepo.findById(webService.getWsMsgId());
			if(optionalWS.isPresent()) {
				logger.debug("Update WebService "+webService.toString());
				WebserviceProperties ws = optionalWS.get();
				ws.setWsMsgId(webService.getWsMsgId());
				ws.setContentType(webService.getContentType());
				ws.setInteractionMessage(webService.getInteractionMessage());
				ws.setBotMethodType(webService.getBotMethodType());
				ws.setWsName(webService.getWsName());
				ws.setOutType(webService.getOutType());
				ws.setTitle(webService.getTitle());
				ws.setListParamName(webService.getListParamName());
				webserviceMessageRepo.save(ws);
				return new ResponseEntity<>(webService, HttpStatus.ACCEPTED);
			}else {
				logger.debug("No Webservice Found");
				throw new NotFoundHandler();
			}
		}else {
			logger.debug("Invalid Data For webservice Null values");
			throw new UnprocessablEHandler();
		}
	}
	
	//Delete exist web service 
	public ResponseEntity<Void> deleteAnExistingWS(Long wsId ){
			boolean isExist = webserviceMessageRepo.existsById(wsId);
			if(isExist){
				logger.debug("Delete WebService Object "+wsId);
				webserviceMessageRepo.deleteById(wsId);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				logger.debug("No Webservice Found");
				throw new NotFoundHandler();
			}
	}
	
	
}
