package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the BOT_TEXT_MESSAGES database table.
 */
@Entity
@Table(name = "bot_text_messages")
@NamedQuery(name = "BotTextMessage.findAll", query = "SELECT b FROM BotTextMessage b")
public class BotTextMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TEXT_MSG_ID")
	@SequenceGenerator(name="TEXT_MESSAGE_SEQ" ,sequenceName="TEXT_MESSAGE_SEQ", initialValue=1000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE , generator="TEXT_MESSAGE_SEQ")
	private Long textMsgId;

	@Column(name = "IS_STATIC",nullable=false)
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@ColumnDefault(value="'1'")
	private Boolean isStatic;

	// bi-directional many-to-one association to BotText
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "TEXT_ID")
	private BotText botText;
	
	@JsonIgnore
	@OneToOne()
	@JoinColumn(name = "messageId")
	private InteractionMessage interactionMessage;
	
	@Transient
	private Long messageId;
	
	
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
	
	public BotTextMessage() {
	}

	public Long getTextMsgId() {
		return this.textMsgId;
	}

	public void setTextMsgId(Long textMsgId) {
		this.textMsgId = textMsgId;
	}

	public Boolean getIsStatic() {
		return this.isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	public BotText getBotText() {
		return this.botText;
	}

	public void setBotText(BotText botText) {
		this.botText = botText;
	}

	public InteractionMessage getInteractionMessage() {
		return interactionMessage;
	}

	public void setInteractionMessage(InteractionMessage interactionMessage) {
		this.interactionMessage = interactionMessage;
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
		return "BotTextMessage [textMsgId=" + textMsgId + ", isStatic=" + isStatic + ", botText=" + botText
				+ ", interactionMessage=" + interactionMessage.toString() + ", isDeleted=" + isDeleted + ", createdDate="
				+ createdDate + ", deletedDate=" + deletedDate + ", createdBy=" + createdBy + ", deletedBy=" + deletedBy
				+ "]";
	}

	

	
	
}