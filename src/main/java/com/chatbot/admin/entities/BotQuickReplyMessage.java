package com.chatbot.admin.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.chatbot.admin.utils.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * The persistent class for the BOT_QUICK_REPLY_MESSAGE database table.
 */

@Entity
@Table(name = "bot_quick_reply_message")
@NamedQuery(name = "BotQuickReplyMessage.findAll", query = "SELECT b FROM BotQuickReplyMessage b")
public class BotQuickReplyMessage extends Template  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "QUICK_MSG_ID")
	@SequenceGenerator(name="QUICK_REPLY_SEQ",sequenceName="QUICK_REPLY_SEQ",initialValue=1000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE , generator="QUICK_REPLY_SEQ")
	private Long quickMsgId;

	@Column(name = "IS_STATIC")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@NotNull(message="NOT_NULL")
	@ColumnDefault(value="'1'")
	private Boolean isStatic;

	// bi-directional many-to-one association to BotButton
	@JsonView(View.Summary.class)
	@OneToMany(mappedBy = "botQuickReplyMessage" , cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<BotButton> botButtons;

	// bi-directional many-to-one association to BotText
	@JsonView(View.Summary.class)
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "TEXT_ID")
	private BotText botText;

	@JsonIgnore
	@OneToOne(orphanRemoval=true)
	@JoinColumn(name = "MESSAGE_ID")
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
	
	
	public BotQuickReplyMessage() {
	}

	public Long getQuickMsgId() {
		return this.quickMsgId;
	}

	public void setQuickMsgId(Long quickMsgId) {
		this.quickMsgId = quickMsgId;
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
		botButton.setBotQuickReplyMessage(this);

		return botButton;
	}

	public BotButton removeBotButton(BotButton botButton) {
		getBotButtons().remove(botButton);
		botButton.setBotQuickReplyMessage(null);

		return botButton;
	}

	public BotText getBotText() {
		return this.botText;
	}

	public void setBotText(BotText botText) {
		this.botText = botText;
	}

	public InteractionMessage getInteractionMessage() {
		return this.interactionMessage;
	}

	public void setInteractionMessage(InteractionMessage interactionMessage) {
		this.interactionMessage = interactionMessage;
	}
	
	
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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
		return "BotQuickReplyMessage [quickMsgId=" + quickMsgId + ", isStatic=" + isStatic + ", Text ID=" + botText.getTextId()
				+ ", messageId=" + interactionMessage.getMessageId() + ", isDeleted=" + isDeleted + ", createdDate=" + createdDate
				+ ", deletedDate=" + deletedDate + ", createdBy=" + createdBy + ", deletedBy=" + deletedBy + "]";
	}

	
	
}