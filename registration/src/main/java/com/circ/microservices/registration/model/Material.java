package com.circ.microservices.registration.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

public class Material {
	
	@Schema (accessMode = AccessMode.AUTO)
	private Integer id;
	
	@Schema (accessMode = AccessMode.READ_WRITE, required = true)
	private String code;
	
	@Schema (accessMode = AccessMode.READ_WRITE, required = true)
	private String name; 
	
	@Schema (accessMode = AccessMode.READ_WRITE, required = true)
	private Boolean isRecyclable; 
	
	@Schema (accessMode = AccessMode.READ_WRITE, required = true)
	private Integer recycleCode; 
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private String type; 
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private String subTypeGroup; 
	
	@Schema (accessMode = AccessMode.READ_WRITE)
	private String subType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsRecyclable() {
		return isRecyclable;
	}
	public void setIsRecyclable(Boolean isRecyclable) {
		this.isRecyclable = isRecyclable;
	}
	public Integer getRecycleCode() {
		return recycleCode;
	}
	public void setRecycleCode(Integer recycleCode) {
		this.recycleCode = recycleCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubTypeGroup() {
		return subTypeGroup;
	}
	public void setSubTypeGroup(String subTypeGroup) {
		this.subTypeGroup = subTypeGroup;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	

}
