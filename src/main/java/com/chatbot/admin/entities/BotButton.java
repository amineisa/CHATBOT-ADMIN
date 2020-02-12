package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.chatbot.admin.utils.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The persistent class for the BOT_BUTTONS database table.
 */
@ApiModel
@Entity
@Table(name = "bot_buttons")
@NamedQuery(name = "BotButton.findAll", query = "SELECT b FROM BotButton b")
public class BotButton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BUTTON_ID", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="BUTTON_SEQ")
	@SequenceGenerator(sequenceName="BUTTON_SEQ",allocationSize=1,initialValue=1000 , name="BUTTON_SEQ")
	@ApiModelProperty(notes = "Button Id")
	private Long buttonId;

	@Column(name = "BUTTON_PAYLOAD",nullable=true)
	@ApiModelProperty(notes = "Button payload in case button is postback ")
	private String buttonPayload;

	@JsonView(View.Summary.class)
	@JoinColumn(name = "BUTTON_TYPE",referencedColumnName="ID")
	@ManyToOne(fetch=FetchType.EAGER)
	@ApiModelProperty(notes = "button Type ")
	private ButtonType buttonType;

	@Column(name = "BUTTON_URL",nullable=true)
	@ApiModelProperty("Button URL in case button is URL using for webview ")
	private String buttonUrl;

	// bi-directional many-to-one association to BotText
	@JsonView(View.Summary.class)
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "TEXT_ID")
	@ApiModelProperty(notes = "Text to be label for english and arabic value ")
	private BotText botText;

	// bi-directional many-to-one association to BotQuickReplyMessage
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "QUICK_MSG_ID")
	@ApiModelProperty(notes = "Quick Reply Message if this button included as an option of quick reply buttons")
	private BotQuickReplyMessage botQuickReplyMessage;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ELEMENT_ID")
	@ApiModelProperty(notes = "Generic Template's  Element  in case this button included as an element's button ")
	private BotTemplateElement botTemplateElement;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "BUTTON_TEMP_ID")
	@ApiModelProperty(notes = "Button template in case this button included as buttontemplate's buttons")
	private BotButtonTemplateMSG botButtonTemplateMSG;
	
	@Transient
	private Long buttonTemplateId;
	@Transient
	private Long elementId;
	@Transient
	private Long quickReplyId; 
		
	@JsonIgnore
	@Column(name = "IS_DELETED")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@ColumnDefault(value = "'0'")
	private Boolean isDeleted;
	@JsonIgnore
	@Column(name="CREATION_DATE")
	private Timestamp createdDate;
	@JsonIgnore
	@Column(name="DELETED_DATE")
	private Timestamp deletedDate;
	@JsonIgnore
	@Column(name="CREATION_USER_NAME")
	private String createdBy;
	@JsonIgnore
	@Column(name="DELETION_User_name")
	private String deletedBy;
	

	public Long getButtonId() {
		return this.buttonId;
	}

	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	public String getButtonPayload() {
		return this.buttonPayload;
	}

	public void setButtonPayload(String buttonPayload) {
		this.buttonPayload = buttonPayload;
	}

	public ButtonType getButtonType() {
		return this.buttonType;
	}

	public void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
	}

	public String getButtonUrl() {
		return this.buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public BotText getBotText() {
		return this.botText;
	}

	public void setBotText(BotText botText) {
		this.botText = botText;
	}

	public BotQuickReplyMessage getBotQuickReplyMessage() {
		return this.botQuickReplyMessage;
	}

	public void setBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		this.botQuickReplyMessage = botQuickReplyMessage;
	}

	public BotTemplateElement getBotTemplateElement() {
		return botTemplateElement;
	}

	public void setBotTemplateElement(BotTemplateElement botTemplateElement) {
		this.botTemplateElement = botTemplateElement;
	}


	public BotButtonTemplateMSG getBotButtonTemplateMSG() {
		return botButtonTemplateMSG;
	}

	public void setBotButtonTemplateMSG(BotButtonTemplateMSG botButtonTemplateMSG) {
		this.botButtonTemplateMSG = botButtonTemplateMSG;
	}

	
	public Long getButtonTemplateId() {
		return buttonTemplateId;
	}

	public void setButtonTemplateId(Long buttonTemplateId) {
		this.buttonTemplateId = buttonTemplateId;
	}

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Long getQuickReplyId() {
		return quickReplyId;
	}

	public void setQuickReplyId(Long quickReplyId) {
		this.quickReplyId = quickReplyId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Timestamp deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}
	
	
	public boolean checkEmpty() {
		boolean valid = true;
		if(elementId == null && quickReplyId == null && buttonTemplateId == null ||
				(buttonType.getButtonName().equals("postback") && buttonPayload.length()== 0) ||
				(buttonType.getButtonName().equals("url") && buttonUrl.length()== 0)) {
				valid = false;
		}
		return valid;
	}

	@Override
	public String toString() {
		return "BotButton [buttonId=" + buttonId + ", buttonPayload=" + buttonPayload + ", buttonType=" + buttonType
				+ ", buttonUrl=" + buttonUrl + ", botText=" + botText + ", buttonTemplateId=" + buttonTemplateId
				+ ", elementId=" + elementId+ ", quickReplyId=" + quickReplyId + ", isDeleted=" + isDeleted
				+ ", createdDate=" + createdDate + ", deletedDate=" + deletedDate + ", createdBy=" + createdBy
				+ ", deletedBy=" + deletedBy + "]";
	}
	
	

}