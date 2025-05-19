package com.maxkemzi.mypianolist.composer.model;

import java.time.LocalDate;
import java.util.Objects;

import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "composer", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "first_name", "last_name", "bornAt" })
})
public class Composer extends BaseEntity {
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "biography", nullable = false, columnDefinition = "TEXT")
	private String biography;

	@Column(name = "image")
	private String image;

	@Column(name = "born_at", nullable = false)
	private LocalDate bornAt;

	@Column(name = "died_at")
	private LocalDate diedAt;

	protected Composer() {
	}

	public Composer(String firstName, String lastName, String nickname, String biography, String image, LocalDate bornAt,
			LocalDate diedAt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.biography = biography;
		this.image = image;
		this.bornAt = bornAt;
		this.diedAt = diedAt;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public String getNickname() {
		return nickname;
	}

	public String getBiography() {
		return biography;
	}

	public String getImage() {
		return image;
	}

	public LocalDate getBornAt() {
		return bornAt;
	}

	public LocalDate getDiedAt() {
		return diedAt;
	}

	protected boolean entityEquals(Object o) {
		Composer c = (Composer) o;
		return Objects.equals(firstName, c.firstName) && Objects.equals(lastName, c.lastName);
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { firstName, lastName };
	}

	@Override
	public String toString() {
		return "Composer [getId()=" + getId() + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName()
				+ ", getNickname()=" + getNickname() + ", getBiography()="
				+ getBiography() + ", getImage()=" + getImage() + ",  getBornAt()=" + getBornAt() + ", getDiedAt()="
				+ getDiedAt() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
				+ getUpdatedAt() + "]";
	}
}
