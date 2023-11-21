package com.security.basicauth.dto;

import java.util.Set;

import com.security.basicauth.Model.Role;

import lombok.Builder;



@Builder
public record CreateUserRequest(
		
		String name,
		String username,
		String password,
		Set<Role> authorities
		
		
		){



}
