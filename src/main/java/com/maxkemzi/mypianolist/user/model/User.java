package com.maxkemzi.mypianolist.user.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.maxkemzi.mypianolist.user.favouritecomposer.model.FavouriteComposer;
import com.maxkemzi.mypianolist.user.favouritepiece.model.FavouritePiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_account")
public class User extends BaseEntity {
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;

	@Column(name = "avatar")
	private String avatar;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPiece> userPieces = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FavouritePiece> favouritePieces = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FavouriteComposer> favouriteComposers = new ArrayList<>();

	protected User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	protected boolean entityEquals(Object o) {
		User ua = (User) o;
		return Objects.equals(getId(), ua.getId());
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { username, email };
	}

	@Override
	public String toString() {
		return "User [getId()=" + getId()
				+ ", getUsername()=" + getUsername() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword()
				+ ", getAvatar()=" + getAvatar() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
				+ getUpdatedAt() + "]";
	}
}
