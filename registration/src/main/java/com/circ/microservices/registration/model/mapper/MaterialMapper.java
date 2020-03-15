package com.circ.microservices.registration.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.circ.microservices.registration.model.Material;

public class MaterialMapper implements RowMapper<Material> {

	@Override
	public Material mapRow(ResultSet rs, int rowNum) throws SQLException {
		Material material = new Material();
		
		material.setId(rs.getInt("MTL_ID"));
		material.setCode(rs.getString("MTL_CODE"));
		material.setName(rs.getString("MTL_NAME"));
		material.setIsRecyclable(rs.getBoolean("MTL_RECYCLABLE"));
		material.setRecycleCode(rs.getInt("MTL_RECYCLE_CODE"));
		material.setType(rs.getString("MTL_TYPE"));
		material.setSubTypeGroup(rs.getString("MTL_SUB_TYPE_GROUP"));
		material.setSubType(rs.getString("MTL_SUB_TYPE"));
		
		return material;
	}

}
