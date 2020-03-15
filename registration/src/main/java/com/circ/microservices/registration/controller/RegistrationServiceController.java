package com.circ.microservices.registration.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circ.microservices.registration.model.Registration;
import com.circ.microservices.registration.service.RegistrationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
public class RegistrationServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceController.class);

	
	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping(value="/sellers/{id}")
	public ResponseEntity<Object> getSellers(@PathVariable("id") String id) {
		return new ResponseEntity<>(registrationService.getSeller(id),HttpStatus.OK);
	}
	
	
	@Operation(summary = "Find all registrations", description = "Get a list of all registrations in system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Registration.class)))) })	
    
	@GetMapping(value = "/registrations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Registration>> getAllRegistrations(){
		logger.info("Received request to get all registrations");
		return new ResponseEntity<>(registrationService.getAllRegistrations(),HttpStatus.OK);
	}
	
	@GetMapping(value="/registrations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRegistration(@PathVariable("id") String id) {
		logger.info("Received request to get registrations for registration id "+id);
		return new ResponseEntity<>(registrationService.getRegistration(new Integer(id)),HttpStatus.OK);
	}
	
	@PostMapping(value = "/registrations/add", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Registration> registerProduct(@RequestBody Registration registration) {
		logger.info("Got request to add new Registration by user " + registration.getUser());
		registrationService.addRegistration(registration);
		return new ResponseEntity<>(registration, HttpStatus.OK);
	}

}
