package com.circ.microservices.registration.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.circ.microservices.registration.model.Seller;

public class SellerMapper implements RowMapper<Seller>{

	@Override
	public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
		Seller seller = new Seller(); 
		seller.setId(rs.getInt("SLR_ID"));
		seller.setName(rs.getString("SLR_NAME"));
		seller.setLei(rs.getString("SLR_LEI"));
		return seller;
	}

}
