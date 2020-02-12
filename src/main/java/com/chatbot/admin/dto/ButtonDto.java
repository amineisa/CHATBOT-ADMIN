package com.chatbot.admin.dto;

import lombok.Data;

@Data
public class ButtonDto {

	private String buttonPayload;
	private String buttonUrl;
	private Long quickReplyId;
	private Long buttonTemplateId;
	private Long elemnetId;
	private TextDto text;
	private Long buttonTypeId;

	public String getButtonPayload() {
		return buttonPayload;
	}

	public void setButtonPayload(String buttonPayload) {
		this.buttonPayload = buttonPayload;
	}

	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public Long getQuickReplyId() {
		return quickReplyId;
	}

	public void setQuickReplyId(Long quickReplyId) {
		this.quickReplyId = quickReplyId;
	}

	public Long getButtonTemplateId() {
		return buttonTemplateId;
	}

	public void setButtonTemplateId(Long buttonTemplateId) {
		this.buttonTemplateId = buttonTemplateId;
	}

	public Long getElemnetId() {
		return elemnetId;
	}

	public void setElemnetId(Long elemnetId) {
		this.elemnetId = elemnetId;
	}

	public TextDto getText() {
		return text;
	}

	public void setText(TextDto text) {
		this.text = text;
	}

	public Long getButtonTypeId() {
		return buttonTypeId;
	}

	public void setButtonTypeId(Long buttonTypeId) {
		this.buttonTypeId = buttonTypeId;
	}
	
	

}
