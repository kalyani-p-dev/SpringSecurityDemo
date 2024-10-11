package com.zensar;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfiguration {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserDetailsService userDetailsService;
	
	//this case is for database 
	//SecurityConfguration
	
//	@Bean
//	public ProviderManager getAuthenticationManager() {
//		ProviderManager authenticationManager = new ProviderManager(getDaoAuthenticationProvider());
//		return authenticationManager;
//	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager() {
		return new ProviderManager(getDaoAuthenticationProvider());
	}
 
	
//	@Bean
//	public AuthenticationManager getAuthenticationManager() {
//	    return new ProviderManager(Arrays.asList(getDaoAuthenticationProvider()));
//	}

		@Bean
		public DaoAuthenticationProvider getDaoAuthenticationProvider() {
			DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(userDetailsService);
			daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
			return daoAuthenticationProvider;
		}
		//SecurityConfiguration
//		@Bean
//		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//			http.csrf(csrf->csrf.disable())
//			.authorizeHttpRequests(auth->auth
//			.requestMatchers("/user/authenticate").permitAll());
//			return http.build();	
//		}
		//Authorization
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
			http.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(auth->auth
			.requestMatchers("/admin").hasRole("ADMIN")
			.requestMatchers("/user").hasAnyRole("USER", "ADMIN")
			.requestMatchers("/zenuserapp/user/authenticate", "/all","/zenuserapp/token/validate").permitAll()
			.anyRequest().authenticated()
			).formLogin(Customizer.withDefaults());
			return http.build();	
		}
	
		//this case is for in-memory 
//	@Bean
//	public UserDetailsService users() {
//		UserBuilder userBuilder = User.builder();
//		UserDetails tomUser=
//		userBuilder.username("tom").password(passwordEncoder.encode("tom123")).roles("USER").build();
//		UserDetails jerryUser=
//				userBuilder.username("jerry").password(passwordEncoder.encode("jerry123")).roles("ADMIN").build();
//		
//		return new InMemoryUserDetailsManager(tomUser,jerryUser);
//		
//		
//	}
	

}
