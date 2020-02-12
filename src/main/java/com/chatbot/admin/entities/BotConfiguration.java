package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
@Entity
@Table(name="BOT_CONFIGURATION")
public class BotConfiguration implements Serializable{

	private static final long serialVersionUID = 1L;


@Id
@Column(name="ID")
private Long id;


@Column(name="KEY")
private String key;

@Column(name="VALUE")
private String value;

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

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
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
	return Stream.of(key,value).allMatch(Objects::isNull) && key.equals("") && value.equals("");
}
	
}
