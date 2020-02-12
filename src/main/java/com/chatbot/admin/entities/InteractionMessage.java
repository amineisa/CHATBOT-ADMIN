package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

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

/**
 * The persistent class for the BOT_INTERACTION_MESSAGES database table.
 */


@Entity
@Table(name = "bot_interaction_messages")
@NamedQuery(name = "InteractionMessage.findAll", query = "SELECT b FROM InteractionMessage b")
public class InteractionMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MESSAGE_ID")
	@SequenceGenerator(name="MESSAGE_SEQ" ,sequenceName = "MESSAGE_SEQ", initialValue=1000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO , generator="MESSAGE_SEQ")
	private Long messageId;

	@Column(name = "MESSAGE_PRIORITY",nullable=false)
	private Long messagePriority;

	// bi-directional many-to-one association to BotMessageType
	@JsonView(View.Summary.class)
	@ManyToOne()
	@JoinColumn(name = "MESSAGE_TYPE" , referencedColumnName="MESSAGE_TYPE_ID" ,nullable=false)
	private BotMessageType botMessageType;

	// bi-directional many-to-one association to BotInteraction
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "INTERACTION_ID")
	private BotInteraction botInteraction;
	

	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "IS_STATIC" , nullable=false)
	@ColumnDefault(value="'1'")
	private Boolean isStatic;
	@Transient
	@JsonView()
	private Long interactionId;
	
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
	private Long messageTypeId;
	
	public InteractionMessage() {
	}

	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getMessagePriority() {
		return this.messagePriority;
	}

	public void setMessagePriority(Long messagePriority) {
		this.messagePriority = messagePriority;
	}

	public BotMessageType getBotMessageType() {
		return this.botMessageType;
	}
	
	public void setBotMessageType(BotMessageType botMessageType) {
		this.botMessageType = botMessageType;
	}

	public BotInteraction getBotInteraction() {
		return this.botInteraction;
	}

	
	public void setBotInteraction(BotInteraction botInteraction) {
		this.botInteraction = botInteraction;
	}

	public Boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
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

	public Long getInteractionId() {
		return interactionId;
	}

	public void setInteractionId(Long interactionId) {
		this.interactionId = interactionId;
	}
	
	@Override
	public String toString() {
		return "InteractionMessage [messageId=" + messageId + ", messagePriority=" + messagePriority
				+ ", botMessageType=" + botMessageType.getMessageTypeName()+ ", botInteraction ID=" + botInteraction.getInteractionId() + ", isStatic=" + isStatic
				+ ", isDeleted=" + isDeleted + ", createdDate=" + createdDate + ", deletedDate=" + deletedDate
				+ ", createdBy=" + createdBy + ", deletedBy=" + deletedBy + "]";
	}

	public Long getMessageTypeId() {
		return messageTypeId;
	}

	public void setMessageTypeId(Long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	public boolean checkEmpty() {
		return Stream.of(messagePriority,interactionId).allMatch(Objects::isNull) && messagePriority != 0 && interactionId != 0 && botMessageType.checkEmpty();
	}
	
	
}