package com.smoothstack.utopia.counter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.exception.MissingIdException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		ex.getBindingResult().getFieldErrors().stream().forEach(error -> {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		});
		ex.getBindingResult().getGlobalErrors().stream().forEach(error -> {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidIdException.class)
	public ResponseEntity<String> handleInvalidIdException(InvalidIdException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MissingIdException.class, RollbackException.class,
			ConstraintViolationException.class })
	public ResponseEntity<String> handleBadRequestException(Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
