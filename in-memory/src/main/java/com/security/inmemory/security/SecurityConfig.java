package com.security.inmemory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
		
	}
	
	//User details interfacesi sprin içinde mevcut bize metodlar sağlıyor
	//UserDetails User VE buildi import ettik
	//user Classının içerisinde methodlar mevcut
	public UserDetailsService users() {
		
		UserDetails user1 =User.builder()
				.username("ogz")
				.password(passwordEncoder().encode("pass"))
				.roles("USER")
				.build();
		
		UserDetails admin = User.builder()
				.username("brf")
				.password(passwordEncoder().encode("pass"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user1,admin);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security
		.headers(x-> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) //header içerisine custom değerler girilmesini engeller
		.csrf(AbstractHttpConfigurer::disable) //Cross side request for Key
		.formLogin(AbstractHttpConfigurer::disable) // Disable ettiğimiz için ıd ve pw yi header ile yollamamız gerekli 
		                                          // Default configurer olarak kullansakydık security bizim için login sayfası üretecekti
		.authorizeHttpRequests(x -> x.requestMatchers("/public/user/**").hasRole("USER"))    // erişimi vermek isteiğimiz yerleri beilrtiyoruz
		.authorizeHttpRequests(x -> x.requestMatchers("/Private/user/**").hasRole("USER")) 
		.authorizeHttpRequests(x -> x.requestMatchers("/public/**","/auth/**").permitAll())
		.authorizeHttpRequests(x -> x.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults());
		
		return security.build();
	}
	

}
