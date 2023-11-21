package com.security.jwttoken.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.jwttoken.service.JwtService;
import com.security.jwttoken.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	
	private final UserService userService;

	public JwtAuthFilter(JwtService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	//GELEN REQUEST İÇERİSİNDEKİ TOKENİ VALUDATE ET
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		
		//tokenin prefixi ve kendisi var bunları ayırmamız lazım
		String token =null;
		
		
		//karşılaştırma yapabilmek için username ihtiyaç var
		String username=null;
		
		//Auth header ve bearer ile başlıyorsa tokeni alma koşulu yazdık
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			
			token= authHeader.substring(7);  // 7 den sonrasını al
			username= jwtService.extractUser(token);
		}
		// username contexht null olmayacak ve authentication null olacak
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails user =userService.loadUserByUsername(username);
			if (jwtService.validateToken(token, user)) {
				
				//gelen tokenın doğruluğunu kontrol ediyor ilk if koşuluna girersek
				//tokenin bilgisini authenticate atıyoruzki tekaradan bu adımlara sokmasın dyie
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		//validation işlemlerni yapıp geride kalan filter işlemlerine devam ediyor
		filterChain.doFilter(request, response);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
