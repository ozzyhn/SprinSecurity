package com.security.jwttoken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.jwttoken.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	
	private final JwtAuthFilter  jwtAuthFilter;
	
	private final UserService userService;
	
	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserService userService, PasswordEncoder passwordEncoder) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(x -> x
						
						.requestMatchers("/auth/welcome/**","/auth/addNewUser/**","/auth/GenerateToken/**").permitAll()
						.requestMatchers("/auth/user/**").hasRole("USER")
						.requestMatchers("/auth/admın/**").hasRole("ADMIN")
						
						)
				.sessionManagement(x-> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
	//Authentication işlemi yaparken hangi servici ve hangi encodoru kullanacagını belirliyor
	@Bean
	public AuthenticationProvider authenticationProvider() {
		 //veritabanına gideceği için 
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	
	//gelen authentication requesti tokene çevirmeden önce authenticatn işlemini sağlar
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	
	
	
}
