package com.klerman.ibooks.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	public User save(User user);

}
