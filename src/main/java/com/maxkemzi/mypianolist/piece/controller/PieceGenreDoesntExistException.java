package com.maxkemzi.mypianolist.piece.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The genre doesn't exist.")
public class PieceGenreDoesntExistException extends RuntimeException {
}
