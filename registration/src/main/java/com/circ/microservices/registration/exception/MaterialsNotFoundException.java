package com.circ.microservices.registration.exception;

public class MaterialsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MaterialsNotFoundException(String msg) {
		super(msg);
	}
}
