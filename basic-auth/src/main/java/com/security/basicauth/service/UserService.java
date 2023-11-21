package com.security.basicauth.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.basicauth.Model.User;
import com.security.basicauth.dto.CreateUserRequest;
import com.security.basicauth.repository.UserRepository;

@Service
public class UserService {

	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
		
		this.userRepository= userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	public Optional<User>getByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public User createUser(CreateUserRequest request) {
		
		User newUser= User.builder()
				.Name(request.name())
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.authorities(request.authorities())
				.accountNonExpired(true)
				.credentialNonExpired(true)
				.isEnabled(true)
				.accountNonLocked(true)
				.build();
		return userRepository.save(newUser);
	}
	
	

}
