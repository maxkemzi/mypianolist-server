package com.maxkemzi.mypianolist.piece.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The piece doesn't exist.")
public class PieceDoesntExistException extends RuntimeException {
}
