package com.user.springbootcase.core.utilities.concretes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import com.user.springbootcase.core.utilities.abstracts.UserInputValidationService;

@Service
public class UserInputValidationManager implements UserInputValidationService {

	public boolean emailCheck(String email) {
		// TODO Auto-generated method stub

		String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	@Override
	public boolean passwordCheck(String password) {
		// TODO Auto-generated method stub
		if (password.length() >= 8 && password.length() <= 16) {
			return true;
		}
		return false;
	}
}
