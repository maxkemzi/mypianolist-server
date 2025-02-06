package com.maxkemzi.mypianolist.composer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The composer doesn't exist.")
public class ComposerDoesntExistException extends RuntimeException {
}
