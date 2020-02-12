package com.chatbot.admin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the bot_text_response_mapping database table.
 */
@Entity
@Table(name = "bot_text_response_mapping")
@NamedQuery(name = "BotTextResponseMapping.findAll", query = "SELECT b FROM BotTextResponseMapping b")
public class BotTextResponseMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RS_MAP_ID")
	@GeneratedValue(generator="RESPONSE_MAPPING_SEQ",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="RESPONSE_MAPPING_SEQ",sequenceName="RESPONSE_MAPPING_SEQ",initialValue=1000,allocationSize=1)
	private Long rsMapId;

	@Column(name = "COMMON_PATH",nullable=false)
	private String commonPath;

	@Column(name = "EN_PARAMS",nullable=false)
	private String enParams;

	@Column(name = "AR_PARAMS",nullable=false)
	private String arParams;

	// bi-directional many-to-one association to BotWebserviceMessage
	@OneToOne(orphanRemoval=true)
	@JoinColumn(name = "WS_MSG_ID",referencedColumnName="WS_MSG_ID")
	private WebserviceProperties webserviceProperty;

	// bi-directional many-to-one association to BotText
	@OneToOne(orphanRemoval=true)
	@JoinColumn(name = "TEXT_ID",referencedColumnName="TEXT_ID")
	private BotText botText;

	public BotTextResponseMapping() {
	}

	public Long getRsMapId() {
		return this.rsMapId;
	}

	public void setRsMapId(Long rsMapId) {
		this.rsMapId = rsMapId;
	}

	public String getCommonPath() {
		return this.commonPath;
	}

	public void setCommonPath(String commonPath) {
		this.commonPath = commonPath;
	}

	public WebserviceProperties getBotWebserviceMessage() {
		return this.webserviceProperty;
	}

	public void setBotWebserviceMessage(WebserviceProperties webserviceProperty) {
		this.webserviceProperty = webserviceProperty;
	}

	public BotText getBotText() {
		return this.botText;
	}

	public void setBotText(BotText botText) {
		this.botText = botText;
	}

	public String getEnParams() {
		return enParams;
	}

	public void setEnParams(String enParams) {
		this.enParams = enParams;
	}

	public String getArParams() {
		return arParams;
	}

	public void setArParams(String arParams) {
		this.arParams = arParams;
	}

}