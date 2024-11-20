package org.elis.exception.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NoUserLoggedException;
import org.elis.exception.NotAllowedException;
import org.elis.exception.PasswordNotCorrectException;
import org.elis.exception.UsingOldPswException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(EntityIsPresentException.class)
	public ResponseEntity<Map<String, String>> entityIsPresentHandler(EntityIsPresentException ex, WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("error", HttpStatus.BAD_REQUEST.name());
		responseBody.put("errorMessage", ex.getMessage());
		responseBody.put("path", request.getDescription(false));
		return ResponseEntity.badRequest().body(responseBody);
	}

	@ExceptionHandler(NoUserLoggedException.class)
	public ResponseEntity<Map<String, String>> NoUserLoggedHandler(NoUserLoggedException ex, WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("error", HttpStatus.BAD_REQUEST.name());
		responseBody.put("errorMessage", ex.getMessage());
		responseBody.put("path", request.getDescription(false));
		return ResponseEntity.badRequest().body(responseBody);
	}

	@ExceptionHandler(PasswordNotCorrectException.class)
	public ResponseEntity<Map<String, String>> PasswordNotCorrectHandler(PasswordNotCorrectException ex,
			WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("error", HttpStatus.BAD_REQUEST.name());
		responseBody.put("errorMessage", ex.getMessage());
		responseBody.put("path", request.getDescription(false));
		return ResponseEntity.badRequest().body(responseBody);
	}

	@ExceptionHandler(CheckFieldException.class)
	public ResponseEntity<Map<String, String>> checkFieldHandler(CheckFieldException ex, WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("error", HttpStatus.BAD_REQUEST.name());
		responseBody.put("errorMessage", ex.getMessage());
		responseBody.put("path", request.getDescription(false));
		return ResponseEntity.badRequest().body(responseBody);
	}

	@ExceptionHandler(NotAllowedException.class)
	public ResponseEntity<Map<String, String>> NotAllowedHandler(NotAllowedException ex, WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("error", HttpStatus.BAD_REQUEST.name());
		responseBody.put("errorMessage", ex.getMessage());
		responseBody.put("path", request.getDescription(false));
		return ResponseEntity.badRequest().body(responseBody);
	}

	@ExceptionHandler(UsingOldPswException.class)
	public ResponseEntity<Map<String, String>> UsingOldPswHandler(UsingOldPswException ex, WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("error", HttpStatus.BAD_REQUEST.name());
		responseBody.put("errorMessage", ex.getMessage());
		responseBody.put("path", request.getDescription(false));
		return ResponseEntity.badRequest().body(responseBody);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, String>> EntityNotFoundHandler(EntityNotFoundException ex, WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("error", HttpStatus.BAD_REQUEST.name());
		responseBody.put("errorMessage", ex.getMessage());
		responseBody.put("path", request.getDescription(false));
		return ResponseEntity.badRequest().body(responseBody);
	}
}
