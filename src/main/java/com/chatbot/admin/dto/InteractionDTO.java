package com.chatbot.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@ApiModel
public class InteractionDTO {
	@ApiModelProperty
	private String interactionName;
	@ApiModelProperty
	private String payload;
	@ApiModelProperty
	private Boolean isSecure;
	@ApiModelProperty
	private String parentPayLoad;
	//private List<Long> messages;
	
	
	

}
