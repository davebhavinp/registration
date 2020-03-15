package com.circ.microservices.registration.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.circ.microservices.registration.dao.RegistrationDAO;
import com.circ.microservices.registration.model.Registration;

public class RegistrationMapper implements RowMapper<Registration> {

	
	private RegistrationDAO registrationDAO;
	
	public RegistrationMapper(RegistrationDAO registrationDAO) {
		this.registrationDAO = registrationDAO;
	}
	
	
	@Override
	public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {

		Registration registration = new Registration(); 
		
		registration.setId(rs.getInt("REG_ID"));
		registration.setRegistrationDate(rs.getTimestamp("REG_DATE"));
		registration.setUpdateDate(rs.getTimestamp("REG_UPDATE_DATE"));
		registration.setStatus(rs.getString("REG_STATUS"));
		registration.setUser(rs.getString("REG_USER"));
		registration.setComments(rs.getString("REG_COMMENTS"));
		
		registration.setProduct(registrationDAO.getProductById(new Integer(rs.getInt("PRD_ID"))));
		
		return registration;
	}

}
