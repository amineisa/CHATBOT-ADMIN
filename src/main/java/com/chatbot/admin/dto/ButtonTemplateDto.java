package com.chatbot.admin.dto;

import java.util.List;

import lombok.ToString;

@ToString
public class ButtonTemplateDto {

	private TextDto text;
	private Boolean isStatic;
	private Long messageId;
	private List<ButtonDto> buttons;

	public TextDto getText() {
		return text;
	}

	public void setText(TextDto text) {
		this.text = text;
	}

	public Boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public List<ButtonDto> getButtons() {
		return buttons;
	}

	public void setButtons(List<ButtonDto> buttons) {
		this.buttons = buttons;
	}

}
