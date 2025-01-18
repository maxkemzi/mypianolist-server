package com.example.mypianolist.piece.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such genre.")
public class PieceGenreNotFoundException extends RuntimeException {
}
