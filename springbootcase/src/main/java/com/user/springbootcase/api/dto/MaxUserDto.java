package com.user.springbootcase.api.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class MaxUserDto implements Serializable{

	/**
	 * Default
	 */
	private static final long serialVersionUID = -8677004241049246214L;
	

	private String userName;
	
	private String password;
	
	private String email;

	private String type;
	
	private String role;
	

}
