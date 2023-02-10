package com.user.springbootcase.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.user.springbootcase.dataAccess.MaxUserDao;
import com.user.springbootcase.entities.MaxUser;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private MaxUserDao userDao;
    private JwtUtils jwtUtils;
    
    @Autowired
	public JwtAuthenticationFilter(MaxUserDao userDao, JwtUtils jwtUtils) {
		this.userDao = userDao;
		this.jwtUtils = jwtUtils;
	}
    
    
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String userName;
		final String jwtToken;
		
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwtToken = authHeader.substring(7);
		// Debugta bakıldı token ile usernami getiriyor sorun yok.
		userName = jwtUtils.extractUserName(jwtToken);
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			MaxUser user = userDao.findByUserName(userName);
			UserDetails userDetails = JwtUserDetails.create(user);
			
			if(jwtUtils.isTokenValid(jwtToken, userDetails)){
				// isTokenValidimi true döndürdü debugta ve aşağıya geldi şimdi burayı test edicez
                // userDetails nesnemdeki veriler db deki ile eşleşti. 
				// debug ile derine in ve hata sebebini bul.
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
						(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
