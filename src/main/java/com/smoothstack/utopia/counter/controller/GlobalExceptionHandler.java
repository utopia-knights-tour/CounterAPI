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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
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
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(InvalidIdException.class)
	protected ResponseEntity<String> handleInvalidIdException(InvalidIdException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MissingIdException.class, RollbackException.class,
			ConstraintViolationException.class })
	protected ResponseEntity<String> handleBadRequest(Exception ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

}
