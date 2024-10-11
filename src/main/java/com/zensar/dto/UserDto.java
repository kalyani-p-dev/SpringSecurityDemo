package com.zensar.dto;
public class UserDto {
 
	
	int id;
	String username;
	String password;
	String roles;
	
	public UserDto()
	{
		
	}
	public UserDto(int id, String username, String password, String roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	public String getRoles() {
		return roles;
	}
 
	public void setRoles(String roles) {
		this.roles = roles;
	}
 
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles + "]";
	}
	
}