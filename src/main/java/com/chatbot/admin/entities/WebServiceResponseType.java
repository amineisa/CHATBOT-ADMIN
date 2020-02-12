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

import lombok.ToString;

/**
 * The persistent class for the bot_in_out_type database table.
 */
@ToString
@Entity
@Table(name = "bot_in_out_type")
@NamedQuery(name = "WebServiceResponseType.findAll", query = "SELECT b FROM WebServiceResponseType b")
public class WebServiceResponseType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IN_OUT_TYPE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE ,generator="RESPONSE_TYPE_SEQ")
	@SequenceGenerator(name="RESPONSE_TYPE_SEQ" , initialValue=1000)
	private Long responseTypeId;

	@Column(name = "IN_OUT_TYPE_NAME",nullable=false,unique=true)
	private String responseTypeName;
	
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
	
	

	public WebServiceResponseType() {
	}

	public Long getResponseTypeId() {
		return responseTypeId;
	}

	public void setResponseTypeId(Long responseTypeId) {
		this.responseTypeId = responseTypeId;
	}

	public String getResponseTypeName() {
		return responseTypeName;
	}

	public void setResponseTypeName(String responseTypeName) {
		this.responseTypeName = responseTypeName;
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
		return Stream.of(responseTypeId,responseTypeName).allMatch(Objects::isNull)&& responseTypeId!=0 && responseTypeName.equals("");
	}
	
}