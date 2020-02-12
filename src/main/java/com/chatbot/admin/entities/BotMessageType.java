package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the BOT_MESSAGE_TYPES database table.
 */

@Entity
@Table(name = "bot_message_types")
@NamedQuery(name = "BotMessageType.findAll", query = "SELECT b FROM BotMessageType b")
public class BotMessageType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MESSAGE_TYPE_SEQ",sequenceName="MESSAGE_TYPE_SEQ",initialValue=1000,allocationSize=1)
	@GeneratedValue(generator="MESSAGE_TYPE_SEQ" , strategy=GenerationType.SEQUENCE)
	@Column(name = "MESSAGE_TYPE_ID")
	private Long messageTypeId;
	
	@Column(name = "MESSAGE_TYPE_NAME",nullable=false)
	private String messageTypeName;
	
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

	public BotMessageType() {
	}

	public Long getMessageTypeId() {
		return this.messageTypeId;
	}

	public void setMessageTypeId(Long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	public String getMessageTypeName() {
		return this.messageTypeName;
	}

	public void setMessageTypeName(String messageTypeName) {
		this.messageTypeName = messageTypeName;
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

	@Override
	public String toString() {
		return "BotMessageType [messageTypeId=" + messageTypeId + ", messageTypeName=" + messageTypeName + "]";
	}

	public boolean checkEmpty() {
		 return Stream.of(messageTypeName).allMatch(Objects::isNull) && messageTypeName.equals("");
	}
	
	
	

}