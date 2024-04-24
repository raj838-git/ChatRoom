package com.boot.security.Users;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class User implements UserDetails {

	private long id;
	private String email;
	private String username;
	private String name;
	private String password;
	private Date birth;
	private String photourl;

	@Enumerated(EnumType.STRING)
	private static Role role = Role.ADMIN;

	public User(long id, String email, String username, String name, String password, Date birth, String photourl) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.name = name;
		this.password = password;
		this.birth = birth;
		this.photourl = photourl;
	}

	public User() {

	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", email=" + email + ", username=" + username + ", name=" + name + ", password="
//				+ password + ", birth=" + birth + ", role=" + role + "]";
//	}

	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public static Role getRole() {
		return role;
	}

	public static void setRole(Role role) {
		User.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String toString() {
		return "\nUser [id=" + id + ", email=" + email + ", username=" + username + ", name=" + name + ", password="
				+ password + ", birth=" + birth + ", photourl=" + photourl +"]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User user) {
			return this.username.equals(user.getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

}
