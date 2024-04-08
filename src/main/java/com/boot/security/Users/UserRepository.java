package com.boot.security.Users;


import java.sql.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class UserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public User findByUsername(String username) {
		String sql = "SELECT * FROM User WHERE username = ?";
		User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
		return user;
	}
	
	public boolean userByUsername(String username) {
		String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class, username);
		return result>0;
	}
	
	public boolean userByEmail(String email) {
		String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class, email);
		return result>0;
	}
	
	public List<User> findAll() {
		try {
		return jdbcTemplate.query("select * from User", new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException e) {
	        throw new UsernameNotFoundException("User not found", e);
	    }
	}
	
	public int registerUser(String email, String username, String name, String password, Date birth, String photourl) {
		
		String sql = "INSERT INTO User(email, username, name, password, birth, photourl) values (?, ?, ?, ?, ?, ?)";
		int result = jdbcTemplate.update(sql, email, username, name, password, birth, photourl);
		return result;
	}
	
	public String findPhotourlByUsername(String username) {
		User user = jdbcTemplate.queryForObject("select photourl from User where username = ?", new BeanPropertyRowMapper<User>(User.class), username);
		return user.getPhotourl();
	}

}
