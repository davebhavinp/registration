package com.circ.microservices.registration.service;

import java.util.List;

import com.circ.microservices.registration.model.Registration;
import com.circ.microservices.registration.model.Seller;

public interface RegistrationService {
	//Test method only
	public Seller getSeller(String sellerId); 
	
	public Registration getRegistration(Integer registrationId); 
	public List<Registration> getAllRegistrations(); 
	
	public void addRegistration(Registration registration);

}
