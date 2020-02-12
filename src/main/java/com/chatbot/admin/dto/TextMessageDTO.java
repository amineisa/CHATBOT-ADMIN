package com.chatbot.admin.dto;

import lombok.ToString;

@ToString
public class TextMessageDTO {
	private Boolean isStatic;
	private TextDto text;
	private Long interactionMessageId;

	public Boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Long getInteractionMessageId() {
		return interactionMessageId;
	}

	public void setInteractionMessageId(Long interactionMessageId) {
		this.interactionMessageId = interactionMessageId;
	}

	public TextDto getText() {
		return text;
	}

	public void setText(TextDto text) {
		this.text = text;
	}
	
	

}
