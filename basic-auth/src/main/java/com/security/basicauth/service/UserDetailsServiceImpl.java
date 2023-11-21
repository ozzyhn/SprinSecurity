package com.security.basicauth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.basicauth.Model.User;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	private final UserService userService;
	
	public UserDetailsServiceImpl(UserService userService) {
		
		this.userService=userService;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userService.getByUsername(username);
		
		return user.orElseThrow(EntityNotFoundException::new );
	}

}
