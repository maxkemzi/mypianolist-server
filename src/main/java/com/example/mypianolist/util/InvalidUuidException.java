package com.example.mypianolist.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid id format.")
public class InvalidUuidException extends RuntimeException {
}
