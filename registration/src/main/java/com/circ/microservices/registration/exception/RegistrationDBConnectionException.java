package com.circ.microservices.registration.exception;

public class RegistrationDBConnectionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RegistrationDBConnectionException(String msg) {
		super(msg);
	}
	
}
