package com.maxkemzi.mypianolist.composer.service;

import java.time.LocalDate;

public class ComposerCreatePayload {
	private String firstName;
	private String lastName;
	private String nickname;
	private String biography;
	private String photo;
	private LocalDate bornAt;
	private LocalDate diedAt;

	protected ComposerCreatePayload() {
	}

	public ComposerCreatePayload(String firstName, String lastName, String nickname, String biography, String photo,
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
