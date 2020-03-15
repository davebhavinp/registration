package com.circ.microservices.registration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.microservices.registration.dao.RegistrationDAO;
import com.circ.microservices.registration.model.Registration;
import com.circ.microservices.registration.model.Seller;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationDAO registrationDAO; 
	
	
	@Override
	public Seller getSeller(String sellerId) {
		return registrationDAO.getSeller(new Integer(sellerId));
	}


	@Override
	public Registration getRegistration(Integer registrationId) {
		return registrationDAO.getRegistrationById(registrationId);
	}


	@Override
	public List<Registration> getAllRegistrations() {
		return registrationDAO.getAllRegistrations();
	}


	@Override
	public void addRegistration(Registration registration) {
		registrationDAO.addRegistration(registration);
		
	}

}
