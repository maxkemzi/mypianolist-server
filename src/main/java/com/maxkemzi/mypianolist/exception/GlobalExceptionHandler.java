package com.maxkemzi.mypianolist.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.maxkemzi.mypianolist.auth.service.UserWithEmailAlreadyExistsException;
import com.maxkemzi.mypianolist.auth.service.UserWithUsernameAlreadyExistsException;
import com.maxkemzi.mypianolist.auth.service.WrongCredentialsException;
import com.maxkemzi.mypianolist.composer.service.ComposerAlreadyExistsException;
import com.maxkemzi.mypianolist.composer.service.ComposerNotFoundException;
import com.maxkemzi.mypianolist.piece.controller.InvalidPieceSortException;
import com.maxkemzi.mypianolist.piece.genre.service.GenreAlreadyExistsException;
import com.maxkemzi.mypianolist.piece.genre.service.GenreNotFoundException;
import com.maxkemzi.mypianolist.piece.service.PieceAlreadyExistsException;
import com.maxkemzi.mypianolist.piece.service.PieceNotFoundException;
import com.maxkemzi.mypianolist.user.favoritecomposer.service.FavoriteComposerAlreadyExistsException;
import com.maxkemzi.mypianolist.user.favoritecomposer.service.FavoriteComposerNotFoundException;
import com.maxkemzi.mypianolist.user.favoritecomposer.service.FavoriteComposerReachedLimitException;
import com.maxkemzi.mypianolist.user.favoritepiece.service.FavoritePieceAlreadyExistsException;
import com.maxkemzi.mypianolist.user.favoritepiece.service.FavoritePieceNotFoundException;
import com.maxkemzi.mypianolist.user.favoritepiece.service.FavoritePieceReachedLimitException;
import com.maxkemzi.mypianolist.user.piece.controller.InvalidUserPieceSortException;
import com.maxkemzi.mypianolist.user.piece.model.InvalidUserPieceStatusException;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceAlreadyExistsException;
import com.maxkemzi.mypianolist.user.piece.service.UserPieceNotFoundException;
import com.maxkemzi.mypianolist.user.service.SamePasswordException;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobal(Exception e) {
		return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred.", "internal_server_error"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("No resource found.", "no_resource_found"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		if (e.getRequiredType() != null && e.getRequiredType().equals(UUID.class)) {
			return new ResponseEntity<>(new ErrorResponse("Invalid UUID format.", "invalid_uuid_format"), status);
		}

		return new ResponseEntity<>(new ErrorResponse("Invalid parameter.", "invalid_parameter"), status);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleNotValid(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();

		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return new ResponseEntity<>(new ValidationErrorResponse(errors), HttpStatus.BAD_REQUEST);
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

		if (cause instanceof InvalidPieceSortException) {
			return new ResponseEntity<>(new ErrorResponse("Invalid piece sort.", "invalid_piece_sort"), status);
		}

		if (cause instanceof InvalidUserPieceSortException) {
			return new ResponseEntity<>(new ErrorResponse("Invalid user piece sort.", "invalid_user_piece_sort"), status);
		}

		return new ResponseEntity<>(new ErrorResponse("Invalid input format.", "invalid_input_format"), status);
	}

	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationCredentialsNotFound(
			AuthenticationCredentialsNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("Unauthorized.", "unauthorized"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAuthorizationDenied(AuthorizationDeniedException e) {
		return new ResponseEntity<>(new ErrorResponse("Access denied.", "access_denied"), HttpStatus.FORBIDDEN);
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

	@ExceptionHandler(FavoritePieceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleFavoritePieceAlreadyExists(FavoritePieceAlreadyExistsException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The piece is already in your favorites.", "piece_already_in_favorites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavoritePieceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFavoritePieceNotFound(FavoritePieceNotFoundException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The piece is not in your favorites.", "piece_not_in_favorites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavoritePieceReachedLimitException.class)
	public ResponseEntity<ErrorResponse> handleFavoritePieceReachedLimit(FavoritePieceReachedLimitException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The maximum amount of favorite pieces is 10.", "piece_favorites_reached_limit"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavoriteComposerAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleFavoriteComposerAlreadyExists(
			FavoriteComposerAlreadyExistsException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The composer is already in your favorites.", "composer_already_in_favorites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavoriteComposerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFavoriteComposerNotFound(FavoriteComposerNotFoundException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The composer is not in your favorites.", "composer_not_in_favorites"),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FavoriteComposerReachedLimitException.class)
	public ResponseEntity<ErrorResponse> handleFavoriteComposerReachedLimit(FavoriteComposerReachedLimitException e) {
		return new ResponseEntity<>(
				new ErrorResponse("The maximum amount of favorite composers is 10.", "composer_favorites_reached_limit"),
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

	@ExceptionHandler(UserWithUsernameAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserWithUsernameAlreadyExists(UserWithUsernameAlreadyExistsException e) {
		return new ResponseEntity<>(
				new ErrorResponse("User with provided username already exists.", "user_with_username_already_exists"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserWithEmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserWithEmailAlreadyExists(UserWithEmailAlreadyExistsException e) {
		return new ResponseEntity<>(
				new ErrorResponse("User with provided email already exists.", "user_with_email_already_exists"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
		return new ResponseEntity<>(
				new ErrorResponse("Maximum upload size of 500KB exceeded.", "max_upload_size_exceeded"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SamePasswordException.class)
	public ResponseEntity<ErrorResponse> handleSamePassword(SamePasswordException e) {
		return new ResponseEntity<>(
				new ErrorResponse("New password must be different from the current one.", "same_password"),
				HttpStatus.BAD_REQUEST);
	}
}
