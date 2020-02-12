package com.chatbot.admin.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="All_PRIVILEGES")
public class Privilege implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRIVILEGE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="PRIVILEGE_SEQ")
	@SequenceGenerator(initialValue=1,allocationSize=1,name="PRIVILEGE_SEQ")
	private Long id;
	
	@Column(name="PRIVILEGE_NAME")
	private String privilegeName;
	
	@ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	
	
}
