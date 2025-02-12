
package com.maxkemzi.mypianolist.composer.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.composer.model.Composer;

public class ComposerResponseDTO {
	private UUID id;
	private String firstName;
	private String lastName;
	private String nickname;
	private String biography;
	private String photo;
	private LocalDate bornAt;
	private LocalDate diedAt;

	protected ComposerResponseDTO() {
	}

	public ComposerResponseDTO(Composer composer) {
		this.id = composer.getId();
		this.firstName = composer.getFirstName();
		this.lastName = composer.getLastName();
		this.nickname = composer.getNickname();
		this.biography = composer.getBiography();
		this.photo = composer.getPhoto();
		this.bornAt = composer.getBornAt();
		this.diedAt = composer.getDiedAt();
	}

	public UUID getId() {
		return id;
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
