package com.user.springbootcase.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.user.springbootcase.business.concretes.UserDetailsServiceImpl;
import com.user.springbootcase.dataAccess.MaxUserDao;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint handler;
	private final MaxUserDao userDao;
	
	@Autowired
	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
			JwtAuthenticationEntryPoint handler,
			MaxUserDao userDao) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.handler = handler;
		this.userDao = userDao;
	}
	


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/swagger-ui/index.html",
				     "/swagger-ui/swagger-ui.css",
				     "/swagger-ui/swagger-ui-bundle.js",
				     "/swagger-ui/swagger-ui-standalone-preset.js",
				     "/swagger-ui/swagger-initializer.js",
				     "/swagger-ui/index.css",
				     "/swagger-ui/favicon-32x32.png",
				     "/swagger-ui/favicon-16x16.png",
				     "/v3/api-docs/swagger-config",
				     "/v3/api-docs").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/h2-console/**").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/api/auth/login").permitAll()
		.antMatchers("/api/auth/register").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/**/admin/**").hasAuthority("ADMIN")
		.and()
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.headers(headers -> headers.frameOptions().sameOrigin())
		.exceptionHandling().authenticationEntryPoint(handler)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		// TODO Auto-generated method stub
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		// buraya bak.
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(userDao); 
		
	}

}
