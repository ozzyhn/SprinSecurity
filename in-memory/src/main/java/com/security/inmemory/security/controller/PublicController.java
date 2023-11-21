package com.security.inmemory.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@GetMapping
	public String HelloWorldPrivate() {
		return "hello by public";
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String HelloWorldUserPrivate() {
		return "hello by public user";
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String HelloWorldAdmınPrivate() {
		return "hello by public admın";
	}
	
}
