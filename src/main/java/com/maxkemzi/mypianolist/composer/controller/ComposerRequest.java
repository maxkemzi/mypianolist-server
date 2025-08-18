package com.maxkemzi.mypianolist.composer.controller;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class ComposerRequest {
	@NotBlank(message = "First name is required.")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters.")
	private String firstName;

	@NotBlank(message = "Last name is required.")
	@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters.")
	private String lastName;

	@Size(max = 50, message = "Nickname must be at most 50 characters.")
	private String nickname;

	@NotBlank(message = "Biography is required.")
	@Size(min = 10, max = 2000, message = "Biography must be between 10 and 2000 characters.")
	private String biography;

	private String image;

	@NotNull(message = "Born date is required.")
	@Past(message = "Born date must be in the past.")
	private LocalDate bornAt;

	@Past(message = "Died date must be in the past.")
	private LocalDate diedAt;

	protected ComposerRequest() {
	}

	public ComposerRequest(String firstName, String lastName, String nickname, String biography, String image,
			LocalDate bornAt, LocalDate diedAt) {
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
}
