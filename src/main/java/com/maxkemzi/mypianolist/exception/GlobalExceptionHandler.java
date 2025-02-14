package com.maxkemzi.mypianolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.maxkemzi.mypianolist.composer.service.ComposerNotFoundException;
import com.maxkemzi.mypianolist.piece.genre.service.PieceGenreNotFoundException;
import com.maxkemzi.mypianolist.piece.service.PieceNotFoundException;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobal(Exception e) {
		return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred.", "internal_server_error"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The user was not found.", "user_not_found"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PieceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePieceNotFound(PieceNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The piece was not found.", "piece_not_found"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserPieceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserPieceNotFound(UserPieceNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The user piece was not found.", "user_piece_not_found"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PieceGenreNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePieceGenreNotFound(PieceGenreNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The genre was not found.", "genre_not_found"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ComposerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleComposerNotFound(ComposerNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The composer was not found.", "composer_not_found"),
				HttpStatus.NOT_FOUND);
	}
}
