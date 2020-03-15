package com.circ.microservices.registration.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

public class Registration {
	
	// Transactional attributes for registration 
	
	@Schema (description = "A unique registration Id that is auto generated when creating a new Registration", accessMode = AccessMode.AUTO)
	private Integer id; 
	
	@Schema (description = "The timestamp when a registration is entered in system", accessMode = AccessMode.READ_ONLY)
	private Date registrationDate; 
	
	@Schema (accessMode = AccessMode.READ_ONLY)
	private Date updateDate; 
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private String status; 
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private String user;
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private String comments; 
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	// Product for which registration submitted
	private Product product;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	} 
	
	

}
