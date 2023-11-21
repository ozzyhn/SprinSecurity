package com.security.jwttoken.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwttoken.dto.AuthRequest;
import com.security.jwttoken.dto.CreateUserRequest;
import com.security.jwttoken.model.User;
import com.security.jwttoken.service.JwtService;
import com.security.jwttoken.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	
	//controllera iş akışı eklemek hoş değil
	private final UserService service;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;

	public UserController(UserService service, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.service = service;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "allah belanı versin jwt" ;
	}
	
	@PostMapping("/addNewUser")
	public User addUser(@RequestBody CreateUserRequest request) {
	return service.createUser(request);
	}
	
	@PostMapping("/generateToken")
	public String generateToken(@RequestBody AuthRequest request) {
		//authenticaton token userservice tarafında tokena erişir
	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));
	if (authentication.isAuthenticated()) {
		return jwtService.generateToken(request.username());
		
	}
	     throw new UsernameNotFoundException("invalid username{}" + request.username());
	}
	
	@GetMapping("/user")
	public String getUsserString() {
		return "allah belanı versin user" ;
	}
	
	@GetMapping("/admin")
	public String getAdminString() {
		return "allah belanı versin admin" ;
	}
	
	
	
}
