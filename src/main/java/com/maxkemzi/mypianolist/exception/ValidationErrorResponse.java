package com.maxkemzi.mypianolist.exception;

import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {
	private Map<String, String> errors;

	public ValidationErrorResponse(Map<String, String> errors) {
		super("Validation failed.", "invalid_data");

		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
}
