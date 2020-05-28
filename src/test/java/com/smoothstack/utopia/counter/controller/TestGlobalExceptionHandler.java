package com.smoothstack.utopia.counter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smoothstack.utopia.counter.controller.GlobalExceptionHandler;
import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.exception.MissingIdException;

public class TestGlobalExceptionHandler {
	
	@InjectMocks
	private GlobalExceptionHandler controllerAdvice;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testHandleInvalidIdException() {
		String testMessage = "Test Message.";
		InvalidIdException ex = new InvalidIdException(testMessage);
		assertEquals(controllerAdvice.handleInvalidIdException(ex),
				new ResponseEntity<String>(testMessage, HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void testHandleBadRequestException() {
		String testMessage = "Test Message.";
		MissingIdException ex = new MissingIdException(testMessage);
		assertEquals(controllerAdvice.handleBadRequest(ex),
				ResponseEntity.badRequest().body(testMessage));
	}
	
}
