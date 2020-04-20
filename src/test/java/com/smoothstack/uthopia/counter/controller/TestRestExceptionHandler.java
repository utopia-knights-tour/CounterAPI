package com.smoothstack.uthopia.counter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smoothstack.uthopia.counter.exception.InvalidIdException;
import com.smoothstack.uthopia.counter.exception.MissingIdException;
import com.smoothstack.uthopia.counter.exception.NoSeatsAvailableException;

public class TestRestExceptionHandler {
	
	@InjectMocks
	private RestExceptionHandler controllerAdvice;
	
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
	public void testHandleMissingIdException() {
		String testMessage = "Test Message.";
		MissingIdException ex = new MissingIdException(testMessage);
		assertEquals(controllerAdvice.handleMissingIdException(ex),
				new ResponseEntity<String>(testMessage, HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void testHandleNoSeatsAvailableException() {
		String testMessage = "Test Message.";
		NoSeatsAvailableException ex = new NoSeatsAvailableException(testMessage);
		assertEquals(controllerAdvice.handleNoSeatsAvailableException(ex),
				new ResponseEntity<String>(testMessage, HttpStatus.BAD_REQUEST));
	}

}
