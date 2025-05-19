
package com.maxkemzi.mypianolist.composer.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.composer.model.Composer;

public class ComposerResponseDto {
	private UUID id;
	private String firstName;
	private String lastName;
	private String nickname;
	private String biography;
	private String image;
	private LocalDate bornAt;
	private LocalDate diedAt;

	protected ComposerResponseDto() {
	}

	public ComposerResponseDto(Composer c) {
		this.id = c.getId();
		this.firstName = c.getFirstName();
		this.lastName = c.getLastName();
		this.nickname = c.getNickname();
		this.biography = c.getBiography();
		this.image = c.getImage() != null && !c.getImage().isBlank() ? "/images/composers/" + c.getImage() : null;
		this.bornAt = c.getBornAt();
		this.diedAt = c.getDiedAt();
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
