package com.zensar.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zensar.entity.UserEntity;
import com.zensar.repository.UserRepository;

	@Service
	public class UserDetailsServiceImpl implements UserDetailsService {
		@Autowired
		UserRepository userRepository;
		@Autowired
		PasswordEncoder PasswordEncoder;
		
		
	//USERS table in which you have columns like id, username, password, roles
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserEntity> userEntityList =userRepository.findByUsername(username);
		if((userEntityList == null)|| userEntityList.size()==0) { 
			throw new UsernameNotFoundException(username);
			
		}
		UserEntity userEntity = userEntityList.get(0);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		String roles = userEntity.getRoles();//ROLE_ADMIN, ROLE_USER
		String roleArray[] = roles.split(",");
		for(int i=0;i<roleArray.length;i++) {
			authorities.add(new SimpleGrantedAuthority(roleArray[i]));
		}
		User user = new User(username,PasswordEncoder.encode(userEntity.getPassword()), authorities);
		
		return user;
	}
	}
	
	//@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	    List<UserEntity> userEntityList = userRepository.findByUsername(username);
//	    if (userEntityList == null || userEntityList.isEmpty()) {
//	        throw new UsernameNotFoundException("User not found");
//	    }
//
//	    UserEntity userEntity = userEntityList.get(0);
//	    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//	    String roles = userEntity.getRoles();
//	    if (roles != null) {
//	        String[] rolesArray = roles.split(",");
//	        for (String role : rolesArray) {
//	            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.trim()));
//	        }
//	    } else {
//	        // Handle the case when roles are null (assign a default role)
//	        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//	    }
//
//	    //return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
//	    User user = new User(username,PasswordEncoder.encode(userEntity.getPassword()), authorities);
//	    return user;
//	}
//	}
	
		//Using Spring Data JPA communicate with MySQL & load information about given username.
//		 Object userEntity = userRepository.findByUsername(username);
//	        if (userEntity == null) {
//	            throw new UsernameNotFoundException("User not found with username: " + username);
//	        }
//	        return org.springframework.security.core.userdetails.User.builder()
//	                .username(((UserDetails) userEntity).getUsername())
//	                .password(((UserDetails) userEntity).getPassword())
//	                .roles(((UserEntity) userEntity).getRole()) 
//	                .build();
	        
	    
		
	
 
