package com.simakov.diploma.Model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	private String phone;
	@Column(nullable = true)
	private String email;
	@Column(nullable = true)
	private String password;
	@Column(nullable = true)
	private String fullname;
	@Column(nullable = true)
	private Date dob;
	@Column(nullable = true)
	private String gender;
	private String usertype;
}