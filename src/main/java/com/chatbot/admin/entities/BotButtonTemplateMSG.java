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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.chatbot.admin.utils.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Button_Temp_MSG")
@NamedQuery(name = "BotButtonTemplateMSG.findAll", query = "SELECT b FROM BotButtonTemplateMSG b")
public class BotButtonTemplateMSG extends Template implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Button_Template_Id")
	@SequenceGenerator(name = "BUTTON_TEMPLATE_SEQ", allocationSize = 1, initialValue = 1000, sequenceName = "BUTTON_TEMPLATE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUTTON_TEMPLATE_SEQ")
	private Long buttonTempMsgId;

	@Column(name = "IS_STATIC")
	@ColumnDefault(value = "'1'")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean isStatic;

	// bi-directional many-to-one association to BotText
	@JsonView(View.Summary.class)
	@JoinColumn(name= "TEXT_ID" , nullable=false)
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private BotText botText;

	@JsonIgnore
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "MESSAGE_ID",unique=true)
	private InteractionMessage interactionMessage;

	@JsonView(View.Summary.class)
	@OneToMany(mappedBy = "botButtonTemplateMSG",  cascade = CascadeType.ALL)
	private List<BotButton> buttons;
	
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
	private Long messageId;
	
	public Long getButtonTempMsgId() {
		return buttonTempMsgId;
	}

	public void setButtonTempMsgId(Long buttonTempMsgId) {
		this.buttonTempMsgId = buttonTempMsgId;
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

	/*public void addBotText(BotText botText) {
        this.botText = botText;
        botText.setBotButtonTemplate(this);
    }
 
    public void removeBotText(BotText botText) {
        if (botText != null) {
        	botText.setBotButtonTemplate(null);
        }
        this.botText = null;
    }*/
		
	public List<BotButton> getButtons() {
		return buttons;
	}

	public void setButtons(List<BotButton> buttons) {
		this.buttons = buttons;
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
		return "BotButtonTemplateMSG [buttonTempMsgId=" + buttonTempMsgId + ", isStatic=" + isStatic + ", botText="
				+ botText.toString() + ", interactionMessage=" + interactionMessage.toString() + ", isDeleted="
				+ isDeleted + ", createdDate=" + createdDate + ", deletedDate=" + deletedDate + ", createdBy="
				+ createdBy + ", deletedBy=" + deletedBy + "]";
	}

	
	
	
}
