package com.security.jwttoken.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtService {
	
	
	@Value("${jwt.key}")
	private String SECRET;
	
	//Oluşturdugumuz tokeni validate etmemiz gerkeiyor
	public Boolean validateToken (String token,UserDetails userDetails) {
		String username= extractUser(token);
		Date expirationDate = extractExpiration(token);
		
		return userDetails.getUsername().equals(username) && expirationDate.after(new Date());
	}
	
	// aynı şekilde username de extract ediyoruz
	public String extractUser(String token) {
		@SuppressWarnings("deprecation")
		Claims claims = Jwts
				.parser()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	// gelen tokenlerin süresinin ne zaman geçtipi
	private Date extractExpiration(String token) {
		//gelen tokenı parçalaıyoruz ama elimizdeki keyle date içeriğine erişmek için
		@SuppressWarnings("deprecation")
		Claims claims = Jwts
				.parser()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getExpiration();

	}

	//genereate token tarafında map ile string ve obje türünden bir map oluşturduk
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		
		return createToken(claims,username);
		

	}
	//create token ile token üretiyoruz
	@SuppressWarnings("deprecation")
	private String createToken(Map<String, Object>claims,String username) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000*60*2))
				.signWith(getSignKey(),SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	// elde etmemiz gereken key için oluşturduk signwith tarafına oluşturdugumuz keyi verdik
	private Key getSignKey() {
		byte[] keyBytes =Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	

}
