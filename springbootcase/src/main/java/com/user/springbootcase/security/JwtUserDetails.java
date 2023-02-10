package com.user.springbootcase.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.user.springbootcase.entities.MaxUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUserDetails implements UserDetails {

	/**
	 * Default
	 */
	private static final long serialVersionUID = 6676862052122038327L;
	
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	private JwtUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static JwtUserDetails create(MaxUser user) {
		List<GrantedAuthority> authoritiesList = new ArrayList<>();
		authoritiesList.add(new SimpleGrantedAuthority(user.getRole()));
		return new JwtUserDetails(user.getUserName(), user.getPassword(), authoritiesList);
	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
