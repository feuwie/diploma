package com.simakov.diploma.Repository;

import com.simakov.diploma.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmailAndUsertype(String email, String usertype);

	User findByPhoneAndUsertype(String phone, String usertype);

	void deleteByPhoneAndUsertype(String phone, String usertype);
}
