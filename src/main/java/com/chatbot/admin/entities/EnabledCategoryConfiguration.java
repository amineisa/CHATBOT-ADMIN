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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
@Entity
@Table(name="Enabled_Category_Configuration")
public class EnabledCategoryConfiguration implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(generator="ENABLED_CATEGORIES_SEQ" , strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ENABLED_CATEGORIES_SEQ" ,sequenceName="ENABLED_CATEGORIES_SEQ",allocationSize=1, initialValue=1000)
	private Long id;
	
	@Column(name="English_Categories",nullable=false)
	private String englishCategories;

	@Column(name="Arabic_Categories",nullable=false)
	private String arabicCategories;
	

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

	public String getEnglishCategories() {
		return englishCategories;
	}

	public void setEnglishCategories(String englishCategories) {
		this.englishCategories = englishCategories;
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

	public String getArabicCategories() {
		return arabicCategories;
	}

	public void setArabicCategories(String arabicCategories) {
		this.arabicCategories = arabicCategories;
	}


	public boolean checkEmpty() {
		return Stream.of(englishCategories,arabicCategories).allMatch(Objects::isNull) && englishCategories.equals("") && arabicCategories.equals("");
	}

	

}
