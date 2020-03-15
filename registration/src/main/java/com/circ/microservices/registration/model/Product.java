package com.circ.microservices.registration.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

public class Product {

	@Schema (accessMode = AccessMode.AUTO)
	private Integer id; 
	
	@Schema (accessMode = AccessMode.READ_WRITE, required = true)
	private String name;
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private Long srNo; 
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private Long barCode; 
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private String content;
	
	private Seller seller; 
	private List<Material> materials; 
	
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public List<Material> getMaterials() {
		return materials;
	}
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
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
	public Long getSrNo() {
		return srNo;
	}
	public void setSrNo(Long srNo) {
		this.srNo = srNo;
	}
	public Long getBarCode() {
		return barCode;
	}
	public void setBarCode(Long barCode) {
		this.barCode = barCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	} 
	
	
}
