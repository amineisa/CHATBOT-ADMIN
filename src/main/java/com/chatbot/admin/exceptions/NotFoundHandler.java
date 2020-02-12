package com.chatbot.admin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND /*, reason="Resource Not found"*/)
public class NotFoundHandler extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public NotFoundHandler() {
		
	}
	
	public NotFoundHandler(String msg) {
		super(msg);
	}
	
}
