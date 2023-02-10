package com.user.springbootcase.api.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class AuthenticateRequest implements Serializable {
	
	/**
	 * Default
	 */
	private static final long serialVersionUID = 8959350587773545739L;
	
	private String userName;
	
	private String password;

}
