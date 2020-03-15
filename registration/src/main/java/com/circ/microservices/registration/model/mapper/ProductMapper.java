package com.circ.microservices.registration.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.circ.microservices.registration.dao.RegistrationDAO;
import com.circ.microservices.registration.model.Product;
import com.circ.microservices.registration.model.Seller;

public class ProductMapper implements RowMapper<Product> {
	
	private RegistrationDAO registrationDAO; 

	public ProductMapper(RegistrationDAO registrationDAO) {
		this.registrationDAO = registrationDAO;
	}
	
	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("PRD_ID"));
		product.setSrNo(rs.getLong("PRD_SERIAL_NO"));
		product.setBarCode(rs.getLong("PRD_BAR_CODE"));
		product.setName(rs.getString("PRD_NAME"));
		product.setContent(rs.getString("PRD_CONTENT"));
		
		Seller seller = new Seller(); 
		seller.setId(rs.getInt("SLR_ID"));
		seller.setName(rs.getString("SLR_NAME"));
		seller.setLei(rs.getString("SLR_LEI"));
		
		product.setSeller(seller);
		
		product.setMaterials(registrationDAO.getMaterials(product.getId()));
		
		return product;
	}

}
