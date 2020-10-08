package com.ben.backend.movieapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysql.cj.log.Log;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{   
		if ("admin".equals(username)) 
		{   
			
			bCryptPasswordEncoder = new BCryptPasswordEncoder ();
			
		} else {
			
			throw new UsernameNotFoundException("User not found with username: " + username);
			
		}
	}
}