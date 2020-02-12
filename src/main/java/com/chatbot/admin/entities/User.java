package com.chatbot.admin.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="USERS_SEQUENCE")
	@SequenceGenerator(initialValue=1,allocationSize=1,name="USERS_SEQUENCE")
	private Long userId;
	@Column(name="USER_NAME",updatable=false,insertable=false)
	private String userName;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="USER_NAME")
	private String lastName;
	@Column(name="EMAIL")
	private String email;
	@Column(name="ENABLED")
	private boolean enabled;
	@Column(name="TOKEN_EXPIRED")
	private boolean tokenExpired;
	@ManyToMany
	@JoinTable(name = "USERS_ROLES",joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")) 
	private Set<Role> roles;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isTokenExpired() {
		return tokenExpired;
	}
	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}
	
	

}
