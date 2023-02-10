package com.user.springbootcase.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.springbootcase.api.dto.AuthenticateRequest;
import com.user.springbootcase.api.dto.MaxUserDto;
import com.user.springbootcase.business.abstracts.MaxUserService;
import com.user.springbootcase.business.concretes.MaxUserManager;
import com.user.springbootcase.core.utilities.concretes.DataResult;
import com.user.springbootcase.core.utilities.concretes.TokenDataResult;
import com.user.springbootcase.dataAccess.MaxUserDao;
import com.user.springbootcase.security.JwtUserDetails;
import com.user.springbootcase.security.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final MaxUserDao userDao;
	private final JwtUtils jwtUtils;
	private MaxUserService maxUserService;
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager, MaxUserDao userDao, JwtUtils jwtUtils,
			MaxUserManager maxUserService) {
		this.authenticationManager = authenticationManager;
		this.userDao = userDao;
		this.jwtUtils = jwtUtils;
		this.maxUserService = maxUserService;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDataResult<String>> authenticate(@RequestBody AuthenticateRequest request) {
		Authentication auth =authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken
						(request.getUserName(), request.getPassword()));
	 final UserDetails user = JwtUserDetails.create(userDao.findByUserName(request.getUserName()));
     if(user != null) {
    	 SecurityContextHolder.getContext().setAuthentication(auth);
    	 return ResponseEntity.ok(new TokenDataResult<String>(jwtUtils.generateToken(user), true, "Tokenınız oluşturuldu."));
     }
     
      return ResponseEntity.ok(new TokenDataResult<String>(false, "Token oluşturulamadı."));

	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody MaxUserDto maxUserDto) {

		MaxUserDto userDto = new MaxUserDto();
		
		userDto.setUserName(maxUserDto.getUserName());
		userDto.setPassword(maxUserDto.getPassword());
		userDto.setEmail(maxUserDto.getEmail());
		userDto.setType(maxUserDto.getType());
		userDto.setRole(maxUserDto.getRole());

		DataResult<MaxUserDto> userAns = maxUserService.save(userDto);
		if (userAns.isSuccess()) {
			return new ResponseEntity<>(userAns.getMessage(), HttpStatus.CREATED);
		} 	
		
		return new ResponseEntity<>(userAns.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
