package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the BOT_TEXTS database table.
 */
@Entity
@Table(name = "bot_texts")
@NamedQuery(name = "BotText.findAll", query = "SELECT b FROM BotText b")
public class BotText implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TEXT_ID", nullable = false)
	@SequenceGenerator(name = "TEXT_SEQ", sequenceName = "TEXT_SEQ", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(generator = "TEXT_SEQ", strategy = GenerationType.SEQUENCE)
	private Long textId;

	@Column(name = "ARABIC_TEXT", nullable = false)
	private String arabicText;

	@Column(name = "ENGLISH_TEXT", nullable = false)
	private String englishText;	
	
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
	
	

	/*// bi-directional many-to-one association to BotButton
	@JsonIgnore
	@OneToMany(mappedBy = "botText",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<BotButton> botButtons;

	// bi-directional many-to-one association to BotQuickReplyMessage
	@JsonIgnore
	@OneToMany(mappedBy = "botText",orphanRemoval=true,cascade=CascadeType.ALL)
	private List<BotQuickReplyMessage> botQuickReplyMessages;

	// bi-directional many-to-one association to BotTextMessage
	@JsonIgnore
	@OneToMany(mappedBy ="botText",orphanRemoval=true,cascade=CascadeType.ALL)
	private List<BotTextMessage> botTextMessages;

	// bi-directional many-to-one association to BotTextMessage
	@JsonIgnore
	@OneToOne()
	private BotButtonTemplateMSG botButtonTemplate;*/

	public BotText() {
	}

	public Long getTextId() {
		return this.textId;
	}

	public void setTextId(Long textId) {
		this.textId = textId;
	}

	public String getArabicText() {
		return this.arabicText;
	}

	public void setArabicText(String arabicText) {
		this.arabicText = arabicText;
	}

	public String getEnglishText() {
		return this.englishText;
	}

	public void setEnglishText(String englishText) {
		this.englishText = englishText;
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
		return "BotText [textId=" + textId + ", arabicText=" + arabicText + ", englishText=" + englishText + "]";
	}
	
	public boolean checkEmpty() {
		return  arabicText.equals("") || englishText.equals("");
	}
	
	
	

/*	public List<BotButton> getBotButtons() {
		return this.botButtons;
	}

	public void setBotButtons(List<BotButton> botButtons) {
		this.botButtons = botButtons;
	}

	public BotButton addBotButton(BotButton botButton) {
		getBotButtons().add(botButton);
		botButton.setBotText(this);

		return botButton;
	}

	public BotButton removeBotButton(BotButton botButton) {
		getBotButtons().remove(botButton);
		botButton.setBotText(null);
		return botButton;
	}

	public List<BotQuickReplyMessage> getBotQuickReplyMessages() {
		return this.botQuickReplyMessages;
	}

	public void setBotQuickReplyMessages(List<BotQuickReplyMessage> botQuickReplyMessages) {
		this.botQuickReplyMessages = botQuickReplyMessages;
	}

	public BotQuickReplyMessage addBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		getBotQuickReplyMessages().add(botQuickReplyMessage);
		botQuickReplyMessage.setBotText(this);
		return botQuickReplyMessage;
	}

	public BotQuickReplyMessage removeBotQuickReplyMessage(BotQuickReplyMessage botQuickReplyMessage) {
		getBotQuickReplyMessages().remove(botQuickReplyMessage);
		botQuickReplyMessage.setBotText(null);
		return botQuickReplyMessage;
	}

	public List<BotTextMessage> getBotTextMessages() {
		return this.botTextMessages;
	}

	public void setBotTextMessages(List<BotTextMessage> botTextMessages) {
		this.botTextMessages = botTextMessages;
	}

	public BotTextMessage addBotTextMessage(BotTextMessage botTextMessage) {
		getBotTextMessages().add(botTextMessage);
		botTextMessage.setBotText(this);
		return botTextMessage;
	}

	public BotTextMessage removeBotTextMessage(BotTextMessage botTextMessage) {
		getBotTextMessages().remove(botTextMessage);
		botTextMessage.setBotText(null);
		return botTextMessage;
	}

	public BotButtonTemplateMSG getBotButtonTemplate() {
		return botButtonTemplate;
	}

	public void setBotButtonTemplate(BotButtonTemplateMSG botButtonTemplate) {
		this.botButtonTemplate = botButtonTemplate;
	}*/

	

}