package com.chatbot.admin.dto;

import lombok.ToString;

@ToString
public class MessageDTO {
	
	private Long messagePriority;
	private Long messageTypeId;
	private Long interactionId;
	private Boolean isStatic;
	
	public Long getMessagePriority() {
		return messagePriority;
	}
	public void setMessagePriority(Long messagePriority) {
		this.messagePriority = messagePriority;
	}
	
	public Long getMessageTypeId() {
		return messageTypeId;
	}
	public void setMessageTypeId(Long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}
	public Long getInteractionId() {
		return interactionId;
	}
	public void setInteractionId(Long interactionId) {
		this.interactionId = interactionId;
	}
	public Boolean getIsStatic() {
		return isStatic;
	}
	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	
}
