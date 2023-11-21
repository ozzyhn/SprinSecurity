package com.security.jwttoken.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.jwttoken.dto.CreateUserRequest;
import com.security.jwttoken.model.User;
import com.security.jwttoken.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
		
		this.userRepository= userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByUsername(username);
		return user.orElseThrow(EntityNotFoundException::new );
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
