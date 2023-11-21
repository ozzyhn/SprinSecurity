package com.security.basicauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {
	
	
	@GetMapping
	public String HelloWorldPrivate() {
		return "hello by private";
	}

	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String HelloWorldUserPrivate() {
		return "hello by private user";
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String HelloWorldAdmınPrivate() {
		return "hello by private admın";
	}
	
}
