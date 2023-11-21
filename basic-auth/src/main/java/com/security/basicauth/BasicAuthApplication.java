package com.security.basicauth;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.security.basicauth.Model.Role;
import com.security.basicauth.dto.CreateUserRequest;
import com.security.basicauth.service.UserService;

@SpringBootApplication
public class BasicAuthApplication implements CommandLineRunner{
	
	private UserService userService;

	public BasicAuthApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BasicAuthApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		createDummyData();
	}
	
	private void createDummyData() {
		
		CreateUserRequest request= CreateUserRequest.builder()
				.name("Emin")
				.username("emin")
				.password("pass")
				.authorities(Set.of(Role.ROLE_USER))
				.build();
				userService.createUser(request);
		
		CreateUserRequest request2 = CreateUserRequest.builder()
				.name("cabbar")
				.username("cabbar")
				.password("pass")
				.authorities(Set.of(Role.ROLE_MOD))
				.build();
				userService.createUser(request);
		
		
		CreateUserRequest request3 = CreateUserRequest.builder()
				.name("Jamal")
				.username("jamal")
				.password("pass")
				.authorities(Set.of(Role.ROLE_ADMIN))
				.build();
				userService.createUser(request);
	}





}
