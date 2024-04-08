package com.boot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.boot.security.Users.UserRepository;

@SpringBootApplication
public class ChatRoomApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(ChatRoomApplication.class, args);
	}

	@Override
	public void run(String...strings) throws Exception {
		logger.info("All users -> {}", repository.findAll());
		logger.info("User found -> {}", repository.findByUsername("admin"));
	}
}
 