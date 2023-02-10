package com.user.springbootcase.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Maxuser")
public class MaxUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Userid")
	private int userId;

	@Column(name = "Name", length = 75)
	private String userName;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "Email", length = 50)
	private String email;

	@Column(name = "Type", length = 15)
	private String type;
	
	@Column(name = "Role", length = 20)
	private String role;
	
	public MaxUser() {
	}

	public MaxUser(String userName, String password, String type,String role,String email) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.type = type;
		this.role = role;
	}

}
