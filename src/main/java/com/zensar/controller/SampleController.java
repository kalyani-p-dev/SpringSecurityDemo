
	package com.zensar.controller;
	 
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManager;
	//import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
	 
	import com.zensar.dto.UserDto;
	 
	@RestController
	@RequestMapping("/zenuserapp")
	public class SampleController {
		
		@Autowired
		AuthenticationManager manager;
		@Autowired
		com.zensar.security.JwtUtils JwtUtils;
		@Autowired
		UserDetailsService userDetailsService;
		
//		@DeleteMapping(value="/user/logout")
//		public ResponseEntity<Boolean> logout(@RequestHeader("Authorization") String token) {
//			
//			//1) Client logs out before token expiry
//				//A) - Confirm it is valid token.
//			if (!userServiceDelegate.isTokenValid(token)) {
//		        // If the token is not valid, it could be because it's expired
//		        return new ResponseEntity<>(false, HttpStatus.OK);
//		    }
//				//B) - Check whether client token is inside blacklisted_tokens table. If yes, return false.
//			BlacklistedToken blacklistedToken = blacklistedTokenRepository.findByToken(token);
//		    if (blacklistedToken != null) {
//		        // If the token is already blacklisted, return false
//		        return new ResponseEntity<>(false, HttpStatus.OK);
//		    }
//				//C) - If no, Persist the token inside blacklisted_tokens(id,token,blacklist_date_time) table & Return true to the client
//		    BlacklistedToken newBlacklistedToken = new BlacklistedToken();
//		    newBlacklistedToken.setToken(token);
//		    newBlacklistedToken.setBlacklistDateTime(LocalDateTime.now());
//		    blacklistedTokenRepository.save(newBlacklistedToken);
//			//2) Client logs out after token expiry
//				//A) - Confirm it is expired token. Return false saying session already expired.
//		    
//		    return new ResponseEntity<>(true, HttpStatus.OK);
//		}
		
		@GetMapping(value="/token/validate")
		public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token){
			String jwt =token.substring(7);
		
			try {
				String username = JwtUtils.extractUsername(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Boolean isTokenValid = JwtUtils.validateToken(jwt, userDetails);
				return new ResponseEntity<Boolean>(isTokenValid,HttpStatus.OK);
			}
			catch(Exception e) {
		e.printStackTrace();		
				return new ResponseEntity<Boolean>(false,HttpStatus.OK);
			}
		}
		
//		@GetMapping(value="/token/validate")
//		public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
//			String jwt = token.substring(7);
//			try {
//				String username = jwtUtils.extractUsername(jwt);
//				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//				boolean isTokenValid = jwtUtils.validateToken(jwt, userDetails);
//				if(isTokenValid==true) {
//					//Check the token not listed inside blacklisted_tokens table. Return false.
//				}
//				return new ResponseEntity<Boolean>(isTokenValid, HttpStatus.OK);
//			}
//			catch(Exception e) {
//				return new ResponseEntity<Boolean>(false, HttpStatus.OK);
//			}
//		}
		
		
	
		@PostMapping(value="/user/authenticate")
		public ResponseEntity<String> authenticate(@RequestBody UserDto userDto)
		{
			try {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword());
				Authentication Authentication=manager.authenticate(usernamePasswordAuthenticationToken);
				String jwtToken = JwtUtils.generateToken(userDto.getUsername());
				return new ResponseEntity<String>(jwtToken,HttpStatus.OK);	
			}
			catch(org.springframework.security.core.AuthenticationException e) {
			 return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	 
			}
		}
	 
		
	 
		@GetMapping(value="/user")
		public String getUser() {
			return "Hello User";
		}
		@GetMapping(value="/admin")
		public String getAdmin() {
			return "Hello Admin";
		}
		@GetMapping(value="/all")
		public String getAll() {
			return "Hello All";
		}
	}


