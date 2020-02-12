package com.chatbot.admin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST /*, reason="Bad request invalid id value"*/)
public class BadRequestHandler extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public BadRequestHandler() {
		
	}
	
	public BadRequestHandler (String msg) {
		super(msg);
	}
	

}
