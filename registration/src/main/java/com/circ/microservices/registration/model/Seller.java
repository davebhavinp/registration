package com.circ.microservices.registration.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

public class Seller {
	
	@Schema (accessMode = AccessMode.AUTO )
	private Integer id; 
	
	@Schema (accessMode = AccessMode.READ_WRITE, required = true)
	private String name;
	
	@Schema (accessMode = AccessMode.READ_WRITE, required = true)
	private String lei;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLei() {
		return lei;
	}
	public void setLei(String lei) {
		this.lei = lei;
	} 
	

}
