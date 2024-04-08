package com.boot.security.controllers;

import java.sql.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.security.Users.UserRepository;

@Service
public class Modell {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public String startsignup(String email, String username, String name, String password, Date birth) {
		// TODO Auto-generated method stub
		//validate email id - 
		
		// validate username - 
		if(username == null) {
			return "Username required";
		}
		else if(username.length()>10) {
			return "Usename length exceed";
		}
		else if (username.contains(" ")) {
			return "blank space not allowed in username";
		}
		
		else if (userRepository.userByUsername(username)) {
			System.out.println("Username Exist");
			return "Username Exist";
		}
		else if (userRepository.userByEmail(email)) {
			System.out.println("Email Exist");
			return "Email Exist";
		}
		else {
			Random random = new Random();
			Integer random_number = random.nextInt(11);
			String photourl = random_number.toString().concat(".jpg");
			System.out.println(photourl);
			logger.info("Photourl Generate by random module : {}", photourl);
			int result = userRepository.registerUser(email, username, name, passwordEncoder.encode(password), birth, photourl);
			if(result == 1 ) {
				System.out.println("User Register Successfully");
				return "Success";
			}
			else {
				System.out.println("Something wrong .... ");
				return "error";
			}
		}
	}
	
	public String Photourl() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		String username = authentication.getName();
		return userRepository.findPhotourlByUsername(username);
	}

	
}
