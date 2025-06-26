package com.maxkemzi.mypianolist.user.profile.model;

import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.maxkemzi.mypianolist.db.BaseEntity;
import com.maxkemzi.mypianolist.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile extends BaseEntity {
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(name = "biography")
	private String biography;

	@Column(name = "avatar")
	private String avatar;

	protected UserProfile() {
	}

	public UserProfile(User user, String biography, String avatar) {
		this.user = user;
		this.biography = biography;
		this.avatar = avatar;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
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
		return new Object[] { user.getUsername(), user.getEmail() };
	}

	@Override
	public String toString() {
		return "UserProfile [getId()=" + getId()
				+ ", getUser()=" + getUser() + ", getBiography()=" + getBiography() + ", getAvatar()=" + getAvatar()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
				+ getUpdatedAt() + "]";
	}
}
