package com.chatbot.admin.utils;

import org.springframework.stereotype.Component;

import com.chatbot.admin.entities.BotButton;

@Component
public class Util {
	
	
	
	public boolean checkButtonRequired(BotButton button) {
		boolean valid = true; 
		
		if(button.getButtonPayload().equals("") && button.getButtonType().getButtonName().equals("postback") ||
				button.getButtonUrl().equals("") && button.getButtonType().getButtonName().equals("url")) {
			valid = false;
		
		}
		return valid;
	}
	
	

}
