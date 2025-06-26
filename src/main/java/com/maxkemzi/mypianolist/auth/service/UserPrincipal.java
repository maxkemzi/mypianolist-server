package com.maxkemzi.mypianolist.auth.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.maxkemzi.mypianolist.user.profile.model.UserProfile;

public class UserPrincipal implements UserDetails {
	private UserProfile user;

	public UserPrincipal(UserProfile user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(user.getUser().getRole().toString()));
	}

	@Override
	public String getPassword() {
		return user.getUser().getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUser().getUsername();
	}

	public String getBiography() {
		return user.getBiography();
	}

	public String getAvatar() {
		return user.getAvatar();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
