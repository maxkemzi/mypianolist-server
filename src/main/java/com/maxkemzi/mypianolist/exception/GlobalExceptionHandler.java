package com.maxkemzi.mypianolist.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.maxkemzi.mypianolist.auth.service.WrongCredentialsException;
import com.maxkemzi.mypianolist.composer.service.ComposerAlreadyExistsException;
import com.maxkemzi.mypianolist.composer.service.ComposerNotFoundException;
import com.maxkemzi.mypianolist.piece.genre.service.GenreAlreadyExistsException;
import com.maxkemzi.mypianolist.piece.genre.service.GenreNotFoundException;
import com.maxkemzi.mypianolist.piece.service.PieceAlreadyExistsException;
import com.maxkemzi.mypianolist.piece.service.PieceNotFoundException;
import com.maxkemzi.mypianolist.user.favouritecomposer.service.FavouriteComposerAlreadyExistsException;
import com.maxkemzi.mypianolist.user.favouritecomposer.service.FavouriteComposerNotFoundException;
import com.maxkemzi.mypianolist.user.favouritepiece.service.FavouritePieceAlreadyExistsException;
import com.maxkemzi.mypianolist.user.favouritepiece.service.FavouritePieceNotFoundException;
import com.maxkemzi.mypianolist.user.piece.model.InvalidUserPieceStatusException;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceAlreadyExistsException;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceNotFoundException;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobal(Exception e) {
		return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred.", "internal_server_error"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		if (e.getRequiredType() != null && e.getRequiredType().equals(UUID.class)) {
			return new ResponseEntity<>(new ErrorResponse("Invalid UUID format.", "invalid_uuid_format"), status);
		}

		return new ResponseEntity<>(new ErrorResponse("Invalid parameter.", "invalid_parameter"), status);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		Throwable cause = e.getMostSpecificCause();
		HttpStatus status = HttpStatus.BAD_REQUEST;

		if (cause instanceof InvalidFormatException ife) {
			if (ife.getTargetType() != null && ife.getTargetType().equals(UUID.class)) {
				return new ResponseEntity<>(new ErrorResponse("Invalid UUID format.", "invalid_uuid_format"), status);
			}
		}

		if (cause instanceof InvalidUserPieceStatusException) {
			return new ResponseEntity<>(new ErrorResponse("Invalid piece status.", "invalid_piece_status"), status);
		}

		return new ResponseEntity<>(new ErrorResponse("Invalid input format.", "invalid_input_format"), status);
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

	@ExceptionHandler(PieceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handlePieceAlreadyExists(PieceAlreadyExistsException e) {
		return new ResponseEntity<>(new ErrorResponse("The piece already exists.", "piece_already_exists"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserPieceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserPieceNotFound(UserPieceNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The piece is not in your list.", "piece_not_in_list"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserPieceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserPieceAlreadyExists(UserPieceAlreadyExistsException e) {
		return new ResponseEntity<>(new ErrorResponse("The piece is already in your list.", "piece_already_in_list"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavouritePieceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleFavouritePieceAlreadyExists(FavouritePieceAlreadyExistsException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The piece is already in your favourites.", "piece_already_in_favourites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavouritePieceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFavouritePieceNotFound(FavouritePieceNotFoundException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The piece is not in your favourites.", "piece_not_in_favourites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavouriteComposerAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleFavouriteComposerAlreadyExists(
			FavouriteComposerAlreadyExistsException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The composer is already in your favourites.", "composer_already_in_favourites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavouriteComposerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFavouriteComposerNotFound(FavouriteComposerNotFoundException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The composer is not in your favourites.", "composer_not_in_favourites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(GenreNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleGenreNotFound(GenreNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The genre was not found.", "genre_not_found"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(GenreAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleGenreAlreadyExists(GenreAlreadyExistsException e) {
		return new ResponseEntity<>(new ErrorResponse("The genre already exists.", "genre_already_exists"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ComposerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleComposerNotFound(ComposerNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("The composer was not found.", "composer_not_found"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ComposerAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleComposerAlreadyExists(ComposerAlreadyExistsException e) {
		return new ResponseEntity<>(new ErrorResponse("The composer already exists.", "composer_already_exists"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(WrongCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleWrongCredentials(WrongCredentialsException e) {
		return new ResponseEntity<>(new ErrorResponse("Wrong credentials.", "wrong_credentials"),
				HttpStatus.BAD_REQUEST);
	}
}
