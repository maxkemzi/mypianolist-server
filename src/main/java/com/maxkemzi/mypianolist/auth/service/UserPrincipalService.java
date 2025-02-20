package com.maxkemzi.mypianolist.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserService;

@Service
public class UserPrincipalService implements UserDetailsService {
	private final UserService userService;

	public UserPrincipalService(UserService userService) {
		this.userService = userService;
	}

	public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userService.findByUsername(username);

			return new UserPrincipal(user);
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException("The username was not found.");
		}
	}
}
