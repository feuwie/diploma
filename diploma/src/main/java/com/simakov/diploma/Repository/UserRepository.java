package com.simakov.diploma.Repository;

import com.simakov.diploma.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	// User findByEmailAndPasswordAndUsertype(String email, String password, String usertype);
	User findByEmailAndUsertype(String email, String usertype);
}
