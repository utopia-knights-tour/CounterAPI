package com.smoothstack.uthopia.counter.exception;

public class MissingIdException extends Exception {

	private static final long serialVersionUID = 2606777933446881867L;
	
	public MissingIdException(String message) {
		super(message);
	}
}
