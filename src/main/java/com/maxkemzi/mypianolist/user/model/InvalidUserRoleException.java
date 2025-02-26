package com.maxkemzi.mypianolist.user.model;

public class InvalidUserRoleException extends RuntimeException {
	public InvalidUserRoleException() {
		super("Invalid role value. Allowed values: user, admin.");
	}
}
