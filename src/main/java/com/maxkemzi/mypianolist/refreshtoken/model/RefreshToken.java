package com.maxkemzi.mypianolist.refreshtoken.model;

import java.util.Objects;

import com.maxkemzi.mypianolist.db.BaseEntity;
import com.maxkemzi.mypianolist.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity {
	@Column(name = "token", nullable = false)
	private String token;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	protected RefreshToken() {
	}

	public RefreshToken(String token, User user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	protected boolean entityEquals(Object o) {
		RefreshToken rt = (RefreshToken) o;
		return Objects.equals(getId(), rt.getId());
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { token, user };
	}

	@Override
	public String toString() {
		return "RefreshToken [getId()=" + getId() + ", getToken()=" + getToken() + ", getUser()=" + getUser()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt() + "]";
	}
}
