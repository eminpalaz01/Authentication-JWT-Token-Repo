package com.user.springbootcase.core.utilities.abstracts;

public interface UserInputValidationService {

	public boolean emailCheck(String email);
	public boolean passwordCheck(String password);
}
