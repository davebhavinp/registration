package com.circ.microservices.registration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.circ.microservices.registration.exception.MaterialsNotFoundException;
import com.circ.microservices.registration.exception.ProductNotFoundException;
import com.circ.microservices.registration.exception.RegistrationException;

@ControllerAdvice
public class RegistrationServiceExceptionController {
	
	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<Object> exception(ProductNotFoundException pe){
		return new ResponseEntity<>("No Product found for the specified id. Please ensure you are using the correct id",HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler(value = RegistrationException.class)
	public ResponseEntity<Object> exception(RegistrationException pe){
		return new ResponseEntity<>("No Registration found for the specified id. Please ensure you are using the correct id",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = MaterialsNotFoundException.class)
	public ResponseEntity<Object> exception(MaterialsNotFoundException me){
		return new ResponseEntity<>("No Materials found for the specified product id. Please ensure you are using the correct id",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception e){
		return new ResponseEntity<>("Internal server error occurred. Please contact system administrator if this error persists. ",HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
