package com.chatbot.admin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the bot_method_type database table.
 * 
 */
@Entity
@Table(name="bot_method_type")
@NamedQuery(name="BotMethodType.findAll", query="SELECT b FROM BotMethodType b")
public class BotMethodType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="METHOD_TYPE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="METHOD_TYPES_SEQ")
	@SequenceGenerator(name="METHOD_TYPES_SEQ",sequenceName="METHOD_TYPES_SEQ",allocationSize =1 ,initialValue=1000)
	private Long methodTypeId;

	@Column(name="METHOD_TYPE_NAME",unique=true ,nullable=false)
	private String methodTypeName;

	public BotMethodType() {
	}

	public Long getMethodTypeId() {
		return this.methodTypeId;
	}

	public void setMethodTypeId(Long methodTypeId) {
		this.methodTypeId = methodTypeId;
	}

	public String getMethodTypeName() {
		return this.methodTypeName;
	}

	public void setMethodTypeName(String methodTypeName) {
		this.methodTypeName = methodTypeName;
	}


}