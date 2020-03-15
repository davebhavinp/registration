package com.circ.microservices.registration.dao;

import java.util.List;

import com.circ.microservices.registration.exception.MaterialsNotFoundException;
import com.circ.microservices.registration.exception.ProductNotFoundException;
import com.circ.microservices.registration.exception.RegistrationException;
import com.circ.microservices.registration.model.Material;
import com.circ.microservices.registration.model.Product;
import com.circ.microservices.registration.model.Registration;
import com.circ.microservices.registration.model.Seller;

public interface RegistrationDAO {

	
	//Registration
	public List<Registration> getAllRegistrations();
	public Registration getRegistrationById(Integer id) throws RegistrationException;
	
	
	public Registration getRegistrationByProductSrNo(Long srNo);
	public Registration getRegistrationByProductBarCode(Long barCode);
	public List<Registration> getAllRegistrationsForSeller(Integer sellerId); 
	public List<Registration> getAllRegistrationsForMaterial(Integer materialId); 
	
	public void addRegistration(Registration reg) throws RegistrationException; 
	
	
	//Product
	public void addProduct(Product p); 
	public void addProductMaterialMapping(Integer productId, Integer materialId);
	public Product getProductById(Integer id) throws ProductNotFoundException;
	public Product getProductBySrNo(Long srNo);
	
	//Material
	public void addPackagingMaterial(Material m);
	public List<Material> getMaterials(Integer productId) throws MaterialsNotFoundException;
	public Material getMaterialByCode(String materialCode) throws MaterialsNotFoundException;
	
	//Seller 
	public void addSeller(Seller seller); 
	public Seller getSeller(Integer id); 
	public Seller getSellerByLEI(String lei);
	


}
