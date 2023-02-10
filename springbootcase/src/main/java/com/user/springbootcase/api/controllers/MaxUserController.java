package com.user.springbootcase.api.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.springbootcase.api.dto.MaxUserDto;
import com.user.springbootcase.business.abstracts.MaxUserService;
import com.user.springbootcase.core.utilities.concretes.DataResult;
import com.user.springbootcase.security.JwtUtils;

@RestController
@RequestMapping("/api/user")
public class MaxUserController {

	private final JwtUtils jwtUtils;
	private final MaxUserService userService;

	@Autowired
	public MaxUserController(MaxUserService userService, JwtUtils jwtUtils) {
		this.userService = userService;
		this.jwtUtils = jwtUtils;
	}

	@GetMapping("/admin/getall")
	public ResponseEntity<DataResult<List<MaxUserDto>>> findAll() {
		return ResponseEntity.ok(userService.findAll());

	}

	@GetMapping("/me")
	public ResponseEntity<DataResult<MaxUserDto>> findByToken(
			@RequestHeader("Authorization") String authorizationHeader) {
		String jwtToken = authorizationHeader.substring(7);
		String username = jwtUtils.extractUserName(jwtToken);

		DataResult<MaxUserDto> userDtoDataResult = userService.findByUserName(username);

		if (userDtoDataResult.getData() != null) {
			userDtoDataResult.setMessage("Tokenın ait olduğu kullanıcı getirildi.");
			return ResponseEntity.ok(userDtoDataResult);
		}

		// Buraya gelmeyecek çünkü securityConfigte burada tokenı istiyor zaten 
		// sadece okunurluk açısından ekledim.
		userDtoDataResult.setMessage("Tokenınızı kontrol ediniz.");
		return ResponseEntity.ok(userDtoDataResult);
	}

}
