package com.maxkemzi.mypianolist.composer.controller;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public class ComposerDTO {
	@NotBlank(message = "First name is required.")
	private String firstName;

	@NotBlank(message = "Last name is required.")
	private String lastName;

	private String nickname;

	@NotBlank(message = "Biography is required.")
	private String biography;

	private String photo;

	@Past(message = "Born date must be in the past.")
	private LocalDate bornAt;

	@Past(message = "Died date must be in the past.")
	private LocalDate diedAt;

	protected ComposerDTO() {
	}

	public ComposerDTO(String firstName, String lastName, String nickname, String biography, String photo,
			LocalDate bornAt, LocalDate diedAt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.biography = biography;
		this.photo = photo;
		this.bornAt = bornAt;
		this.diedAt = diedAt;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNickname() {
		return nickname;
	}

	public String getBiography() {
		return biography;
	}

	public String getPhoto() {
		return photo;
	}

	public LocalDate getBornAt() {
		return bornAt;
	}

	public LocalDate getDiedAt() {
		return diedAt;
	}
}
