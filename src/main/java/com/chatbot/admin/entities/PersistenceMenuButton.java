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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Bot_Persistence_menu_Buttons")
public class PersistenceMenuButton implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PersistenceMenuButton_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE , generator="Persistence_Menu_SEQ")
	@SequenceGenerator(name="Persistence_Menu_SEQ" ,initialValue=1000)
	private Long id;
	
	@JoinColumn(name="Button_id" , referencedColumnName="BUTTON_ID")
	@OneToOne(orphanRemoval=true,optional=false,cascade=CascadeType.ALL)
	private BotButton button;
	
	@Column(name="parent_Id",nullable=true)
	private Long parentId;
	
	@Column(name="priority",nullable=false)
	private Long priority;
	
	@Column(name="IS_NESTED",nullable=false)
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isNested;
	
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

	public BotButton getButton() {
		return button;
	}

	public void setButton(BotButton button) {
		this.button = button;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Boolean getIsNested() {
		return isNested;
	}

	public void setIsNested(Boolean isNested) {
		this.isNested = isNested;
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
	
	
	
}
