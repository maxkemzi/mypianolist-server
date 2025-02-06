package com.maxkemzi.mypianolist.composer.controller;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public class ComposerDTO {
	@NotBlank(message = "First name is required.")
	public String firstName;

	@NotBlank(message = "Last name is required.")
	public String lastName;

	public String nickname;

	@NotBlank(message = "Biography is required.")
	public String biography;

	public String photo;

	@Past(message = "Born date must be in the past.")
	public LocalDate bornAt;

	@Past(message = "Died date must be in the past.")
	public LocalDate diedAt;

	public ComposerDTO() {
	}
}
