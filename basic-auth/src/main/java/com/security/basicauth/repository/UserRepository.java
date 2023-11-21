package com.security.basicauth.repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.basicauth.Model.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

	
	Optional<User> findByUsername(String userName);
}
