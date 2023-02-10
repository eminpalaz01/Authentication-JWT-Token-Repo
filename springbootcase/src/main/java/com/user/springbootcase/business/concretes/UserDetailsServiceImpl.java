package com.user.springbootcase.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.user.springbootcase.dataAccess.MaxUserDao;
import com.user.springbootcase.entities.MaxUser;
import com.user.springbootcase.security.JwtUserDetails;

public class UserDetailsServiceImpl implements UserDetailsService {

	private MaxUserDao userDao;
	
	@Autowired
	public UserDetailsServiceImpl(MaxUserDao userDao) {
		this.userDao = userDao;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		MaxUser user = userDao.findByUserName(username);
		return JwtUserDetails.create(user);
	}

}
