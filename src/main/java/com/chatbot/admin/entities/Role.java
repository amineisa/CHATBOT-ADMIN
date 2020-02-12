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
@Table(name="ROLES")
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROLE_ID")
	@GeneratedValue(generator="ROLE_SEQUENCE",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue=1,allocationSize=1,name="ROLE_SEQUENCE")
	private Long id;
	
	@Column(name="ROLE_NAME")
	private String roleName;
	@ManyToMany
    @JoinTable(name = "ROLES_PRIVILEGES",joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID"),
    inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "PRIVILEGE_ID"))
	private Set<Privilege> privileges;
	
	@ManyToMany(mappedBy="roles")
	private Set<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	
	
}
