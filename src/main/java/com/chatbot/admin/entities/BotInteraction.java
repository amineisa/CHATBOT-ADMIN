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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.chatbot.admin.utils.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.ToString;

/**
 * The persistent class for the BOT_INTERACTIONS database table.
 */
@ToString
@Entity
@Table(name = "bot_interactions")
@NamedQuery(name = "BotInteraction.findAll", query = "SELECT b FROM BotInteraction b")
public class BotInteraction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INTERACTION_ID", nullable = false)
	@SequenceGenerator(name = "INTERACTION_SEQ", sequenceName = "INTERACTION_SEQ", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "INTERACTION_SEQ")
	private Long interactionId;

	@Column(name = "INTERACTION_NAME")
	@NotNull(message = "Interaction's name is required field")
	private String interactionName;

	@Column(name = "PAYLOAD", nullable = false,unique=true)
	@NotNull(message = "Payload is required field")
	private String payload;

	@Column(name = "IS_SECURE")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@ColumnDefault(value = "'1'")
	private Boolean isSecure;

	@Column(name = "PARENT_PAYLOAD", nullable = true)
	private String parentPayload;

	// bi-directional many-to-one association to BotInteractionMessage
	@JsonView(View.SummaryWithDetails.class)
	@OneToMany(mappedBy = "botInteraction", cascade = CascadeType.ALL)
	private List<InteractionMessage> interactionMessages;

	@JsonIgnore
	@Column(name = "IS_DELETED")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@ColumnDefault(value = "'0'")
	private Boolean isDeleted;
	@JsonIgnore
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;
	@JsonIgnore
	@Column(name = "DELETED_DATE")
	private Timestamp deletedDate;
	@JsonIgnore
	@Column(name = "CREATION_USER_NAME")
	private String createdBy;
	@JsonIgnore
	@Column(name = "DELETION_BY")
	private String deletedBy;

	public BotInteraction() {
	}

	public Long getInteractionId() {
		return this.interactionId;
	}

	public void setInteractionId(Long interactionId) {
		this.interactionId = interactionId;
	}

	public String getInteractionName() {
		return this.interactionName;
	}

	public void setInteractionName(String interactionName) {
		this.interactionName = interactionName;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public boolean getIsSecure() {
		return this.isSecure;
	}

	public void setIsSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}

	public void setIsSecure(Boolean isSecure) {
		this.isSecure = isSecure;
	}

	public List<InteractionMessage> getInteractionMessages() {
		return interactionMessages;
	}

	public void setInteractionMessages(List<InteractionMessage> interactionMessages) {
		this.interactionMessages = interactionMessages;
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

	public String getParentPayload() {
		return parentPayload;
	}

	public void setParentPayload(String parentPayload) {
		this.parentPayload = parentPayload;
	}

	public void addInteractionMessage(InteractionMessage message) {
		interactionMessages.add(message);
		message.setBotInteraction(this);
	}

	public void removeComment(InteractionMessage message) {
		message.setBotInteraction(null);
		this.interactionMessages.remove(message);
	}

	@Override
	public String toString() {
		return "BotInteraction [interactionId=" + interactionId + ", interactionName=" + interactionName + ", payload="
				+ payload + ", isSecure=" + isSecure + ", parentPayLoad=" + parentPayload + ", interactionMessages="
				+ interactionMessages + ", isDeleted=" + isDeleted + ", createdDate=" + createdDate + ", deletedDate="
				+ deletedDate + ", createdBy=" + createdBy + ", deletedBy=" + deletedBy + "]";
	}
	
	
	
	

}