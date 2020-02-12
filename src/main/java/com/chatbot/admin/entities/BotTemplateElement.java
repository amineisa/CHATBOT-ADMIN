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
import javax.persistence.ManyToOne;
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
 * The persistent class for the bot_template_element database table.
 */
/**
 * @author A.Eissa
 *
 */
@Entity
@Table(name = "bot_template_element")
@NamedQuery(name = "BotTemplateElement.findAll", query = "SELECT b FROM BotTemplateElement b")
public class BotTemplateElement extends Template implements Serializable {
	private static final long serialVersionUID = 1L;

	 
	@Id
	@Column(name = "ELEMENT_ID")
	@GeneratedValue(generator="ELEMENT_SEQ",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ELEMENT_SEQ" ,sequenceName="ELEMENT_SEQ",allocationSize=1, initialValue=1000)
	private Long elementId;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isStatic;

	// bi-directional many-to-one association to BotButton
	@JsonView(View.Summary.class)
	@OneToMany(mappedBy = "botTemplateElement" ,cascade=CascadeType.ALL)
	private List<BotButton> botButtons;

	// bi-directional many-to-one association to BotGTemplateMessage
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "G_T_MSG_ID",nullable=false,referencedColumnName="G_T_MSG_ID")
	private BotGTemplateMessage botGTemplateMessage;

	// bi-directional many-to-one association to BotText
	@JsonView(View.Summary.class)
	@OneToOne(orphanRemoval=true,cascade=CascadeType.ALL)
	@JoinColumn(name = "TITLE_TEXT_ID",referencedColumnName="TEXT_ID")
	private BotText title;

	// bi-directional many-to-one association to BotText
	@JsonView(View.Summary.class)
	@OneToOne(cascade=CascadeType.ALL ,orphanRemoval=true)
	@JoinColumn(name = "SUB_TITLE_TEXT_ID" , referencedColumnName="TEXT_ID")
	private BotText subTitle;
	
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
	private Long templateId;
	
	
	public Long getElementId() {
		return this.elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getIsStatic() {
		return this.isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	public List<BotButton> getBotButtons() {
		return this.botButtons;
	}

	public void setBotButtons(List<BotButton> botButtons) {
		this.botButtons = botButtons;
	}

	public BotButton addBotButton(BotButton botButton) {
		getBotButtons().add(botButton);
		botButton.setBotTemplateElement(this);

		return botButton;
	}

	public BotButton removeBotButton(BotButton botButton) {
		getBotButtons().remove(botButton);
		botButton.setBotTemplateElement(null);

		return botButton;
	}

	public BotGTemplateMessage getBotGTemplateMessage() {
		return this.botGTemplateMessage;
	}

	public void setBotGTemplateMessage(BotGTemplateMessage botGTemplateMessage) {
		this.botGTemplateMessage = botGTemplateMessage;
	}

	public BotText getTitle() {
		return title;
	}

	public void setTitle(BotText title) {
		this.title = title;
	}

	public BotText getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(BotText subTitle) {
		this.subTitle = subTitle;
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

	
	
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	@Override
	public String toString() {
		return "BotTemplateElement [elementId=" + elementId + ", imageUrl=" + imageUrl + ", isStatic=" + isStatic
				+ ", botGTemplateMessage=" + botGTemplateMessage.toString() + ", title=" + title.toString() + ", subTitle=" + subTitle.toString()
				+ ", isDeleted=" + isDeleted + ", createdDate=" + createdDate + ", deletedDate=" + deletedDate
				+ ", createdBy=" + createdBy + ", deletedBy=" + deletedBy + "]";
	}


	
	
}