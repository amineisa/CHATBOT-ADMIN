package com.chatbot.admin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chatbot.admin.utils.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.ToString;

/**
 * The persistent class for the bot_webservice_message database table.
 */
@ToString
@Entity
@Table(name = "bot_webservice_message")
@NamedQuery(name = "WebserviceProperties.findAll", query = "SELECT b FROM WebserviceProperties b")
public class WebserviceProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WS_MSG_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="WEB_SERVICE_INFO_SEQ")
	@SequenceGenerator(initialValue=1000 ,allocationSize=1, sequenceName="WEB_SERVICE_INFO_SEQ",name="WEB_SERVICE_INFO_SEQ")
	private Long wsMsgId;


	@Column(name = "WS_NAME", nullable=false, unique=true)
	private String wsName;

	@Column(name = "WS_URL",nullable=false)
	private String wsUrl;


	@Column(name = "LIST_PARAM_NAME",nullable=true)
	private String listParamName;

	@Column(name = "CONTENT_TYPE")
	private Long contentType;

	// bi-directional many-to-one association to BotInteractionMessage
	@JsonView(View.Summary.class)
	@OneToOne(orphanRemoval=true)
	@JoinColumn(name = "MESSAGE_ID",referencedColumnName="MESSAGE_ID",nullable=false)
	private InteractionMessage interactionMessage;

	// bi-directional many-to-one association to BotMethodType
	@JsonView(View.Summary.class)
	@ManyToOne
	@JoinColumn(name = "METHOD_TYPE_ID",referencedColumnName="METHOD_TYPE_ID",nullable=false)
	private BotMethodType botMethodType;

	// bi-directional many-to-one association to BotInOutType
	@JsonView(View.Summary.class)
	@ManyToOne()
	@JoinColumn(name = "OUT_TYPE_ID",referencedColumnName="IN_OUT_TYPE_ID")
	private WebServiceResponseType outType;
	
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "TITLE_TEXT_ID",referencedColumnName="TEXT_ID")
	private BotText title;


	
	/**
	 * 
	 */
	public WebserviceProperties() {
	}

	public Long getWsMsgId() {
		return this.wsMsgId;
	}

	public void setWsMsgId(Long wsMsgId) {
		this.wsMsgId = wsMsgId;
	}




	public String getWsName() {
		return this.wsName;
	}

	public void setWsName(String wsName) {
		this.wsName = wsName;
	}

	public String getWsUrl() {
		return this.wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	
	public InteractionMessage getInteractionMessage() {
		return interactionMessage;
	}

	public void setInteractionMessage(InteractionMessage interactionMessage) {
		this.interactionMessage = interactionMessage;
	}

	public BotMethodType getBotMethodType() {
		return this.botMethodType;
	}

	public void setBotMethodType(BotMethodType botMethodType) {
		this.botMethodType = botMethodType;
	}

	public WebServiceResponseType getOutType() {
		return outType;
	}

	public void setOutType(WebServiceResponseType outType) {
		this.outType = outType;
	}


	public Long getContentType() {
		return contentType;
	}

	public void setContentType(Long contentType) {
		this.contentType = 1L;
	}

	public String getListParamName() {
		return listParamName;
	}

	public void setListParamName(String listParamName) {
		this.listParamName = listParamName;
	}



	public BotText getTitle() {
		return title;
	}

	public void setTitle(BotText title) {
		this.title = title;
	}



	
	
}