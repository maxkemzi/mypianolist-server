package com.maxkemzi.mypianolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.maxkemzi.mypianolist.composer.service.ComposerDoesntExistException;
import com.maxkemzi.mypianolist.piece.genre.service.PieceGenreDoesntExistException;
import com.maxkemzi.mypianolist.piece.service.PieceDoesntExistException;
import com.maxkemzi.mypianolist.user.service.UserDoesntExistException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobal(Exception e) {
		return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred.", "internal_server_error"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserDoesntExistException.class)
	public ResponseEntity<ErrorResponse> handleUserDoesntExist(UserDoesntExistException e) {
		return new ResponseEntity<>(new ErrorResponse("User not found.", "user_not_found"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PieceDoesntExistException.class)
	public ResponseEntity<ErrorResponse> handlePieceDoesntExist(PieceDoesntExistException e) {
		return new ResponseEntity<>(new ErrorResponse("Piece not found.", "piece_not_found"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PieceGenreDoesntExistException.class)
	public ResponseEntity<ErrorResponse> handlePieceGenreDoesntExist(PieceGenreDoesntExistException e) {
		return new ResponseEntity<>(new ErrorResponse("Genre not found.", "genre_not_found"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ComposerDoesntExistException.class)
	public ResponseEntity<ErrorResponse> handleComposerDoesntExist(ComposerDoesntExistException e) {
		return new ResponseEntity<>(new ErrorResponse("Composer not found.", "composer_not_found"), HttpStatus.NOT_FOUND);
	}
}
