package com.security.basicauth.Model;

import org.springframework.security.core.GrantedAuthority;



public enum Role implements GrantedAuthority {
	
	//Roller için geliştirilmiş bir implement
	
	//iSTEDİĞİMİZ GİBİ ROL EKLEYEBİLİYORU
	
	
	
	ROLE_USER("USER"),
	
	ROLE_ADMIN("ADMIN"),
	
	ROLE_MOD("MOD");

	
	private String value;
	
	Role(String value){
		this.value=value;
	}
	
	public String getValue() {
		return this.value;
	}
	@Override
	public String getAuthority() {
		
		return name();
	}

}
