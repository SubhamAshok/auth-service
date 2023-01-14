package com.pdd.authenticationservice.advice;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pdd.authenticationservice.exception.DuplicatedUserInfoException;
import com.pdd.authenticationservice.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = DuplicatedUserInfoException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(Exception exception){
		ErrorResponse errorResponse= new ErrorResponse(HttpStatus.CONFLICT.value(), 
				"Generic Exception", Arrays.asList(exception.getMessage()));
		return new ResponseEntity<>(errorResponse, getHeaders(), HttpStatus.CONFLICT);
	}
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleRequestValidationException(MethodArgumentNotValidException exception){
		ErrorResponse errorResponse= new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 
				"Generic Exception", exception.getAllErrors().stream().map(err->err.getDefaultMessage()).toList());
		return new ResponseEntity<>(errorResponse, getHeaders(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception){
		String errorMessage=exception.getMessage();
		ErrorResponse errorResponse= new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"Generic Exception", Arrays.asList(errorMessage.isBlank()?"Internal Server Error":errorMessage));
		return new ResponseEntity<>(errorResponse, getHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
