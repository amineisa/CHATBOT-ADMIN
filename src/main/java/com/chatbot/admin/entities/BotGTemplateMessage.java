package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.chatbot.admin.utils.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;


/**
 * The persistent class for the bot_g_template_message database table.
 * 
 */
@Entity
@Table(name="bot_g_template_message")
@NamedQuery(name="BotGTemplateMessage.findAll", query="SELECT b FROM BotGTemplateMessage b")
public class BotGTemplateMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="G_T_MSG_ID")
	@SequenceGenerator(name="GENERIC_TEMPLATE_SEQ",allocationSize=1,initialValue=1000,sequenceName="GENERIC_TEMPLATE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="GENERIC_TEMPLATE_SEQ")
	private Long templateId;


	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="MESSAGE_ID", nullable = false)
	private InteractionMessage interactionMessage;

	//bi-directional many-to-one association to BotTemplateElement
	@JsonView(View.Summary.class)
	@OneToMany(mappedBy="botGTemplateMessage" ,cascade=CascadeType.ALL)
	private List<BotTemplateElement> botTemplateElements;
	
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name="IS_LIST_TEMPLATE")
	@ColumnDefault(value = "'0'")
	private Boolean isListTemplate;
	
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
	
	@Transient
	private Long messageId;

	
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public InteractionMessage getInteractionMessage() {
		return interactionMessage;
	}

	public void setInteractionMessage(InteractionMessage interactionMessage) {
		this.interactionMessage = interactionMessage;
	}

	public Boolean getIsListTemplate() {
		return isListTemplate;
	}

	public void setIsListTemplate(Boolean isListTemplate) {
		this.isListTemplate = isListTemplate;
	}

	public List<BotTemplateElement> getBotTemplateElements() {
		return this.botTemplateElements;
	}

	public void setBotTemplateElements(List<BotTemplateElement> botTemplateElements) {
		this.botTemplateElements = botTemplateElements;
	}

	public BotTemplateElement addBotTemplateElement(BotTemplateElement botTemplateElement) {
		getBotTemplateElements().add(botTemplateElement);
		botTemplateElement.setBotGTemplateMessage(this);

		return botTemplateElement;
	}

	public BotTemplateElement removeBotTemplateElement(BotTemplateElement botTemplateElement) { 
		getBotTemplateElements().remove(botTemplateElement);
		botTemplateElement.setBotGTemplateMessage(null);

		return botTemplateElement;
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

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "BotGTemplateMessage [templateId=" + templateId + ", Message ID =" + messageId
				+ ", isListTemplate=" + isListTemplate + ", messageId=" + messageId + "]";
	}

	
	
}