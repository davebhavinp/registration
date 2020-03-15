package com.circ.microservices.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.circ.microservices.registration.dao.RegistrationDAO;
import com.circ.microservices.registration.model.Material;
import com.circ.microservices.registration.model.Product;
import com.circ.microservices.registration.model.Registration;
import com.circ.microservices.registration.model.Seller;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class RegistrationApplicationTests {
	

	
	@Autowired
	private RegistrationDAO registrationDAO; 
	private static final Logger logger = LoggerFactory.getLogger(RegistrationApplicationTests.class);

	private  Product product = new Product(); 
	private  Material material1 = new Material(); 
	private  Material material2 = new Material(); 
	private  Seller seller = new Seller(); 
	private  Registration registration = new Registration(); 
	
	@BeforeAll
	public  void setContext() {
		logger.info("BEFORE ALL Called. Initializing Objects");
		seller.setName("Test Seller");
		seller.setLei("2343Lwr23");
		
		material1.setCode("PET");
		material1.setIsRecyclable(true);
		material1.setName("Polysterene Terephthalate");
		material1.setRecycleCode(1);
		
		material2.setCode("PP");
		material2.setIsRecyclable(true);
		material2.setName("Polypropylene");
		material2.setRecycleCode(7);
		
		product.setBarCode(12345678L);
		product.setContent("Test");
		product.setName("Test Product");
		product.setSrNo(12345678L);
		product.setSeller(seller);
		ArrayList<Material> materials = new ArrayList<Material>(); 
		materials.add(material1); 
		materials.add(material2);
		product.setMaterials(materials);
		
		registration.setProduct(product);
		registration.setComments("Test Registration");
		registration.setStatus("ACTIVE");
		registration.setUser("bdave");
		
	}

	@Order(1)
	@Test 
	public void testSellerCrudOperations() {
		
		logger.info("TEST Seller");
		// insert 
		registrationDAO.addSeller(seller);
		
		assertNotNull(seller.getId());
		logger.info("Tested successful insert of seller. Id generated is "+seller.getId());
		
		// Get by Id
		Seller rs = registrationDAO.getSeller(seller.getId());
		assertEquals(seller.getName(), rs.getName());
		assertEquals(seller.getLei(), rs.getLei());
		
		// Get by LEI 
		Seller ls = registrationDAO.getSellerByLEI(seller.getLei()); 
		assertEquals(seller.getId(), ls.getId());
		assertEquals(seller.getName(), ls.getName());
		
	}
	
	@Order(2)
	@Test 
	public void testMaterialCrudOperations() {
		
		
		logger.info("TEST Material");
		
		// insert
		registrationDAO.addPackagingMaterial(material1);
		registrationDAO.addPackagingMaterial(material2);
		
		assertNotNull(material1.getId());
		logger.info("Tested adding material. Got id "+material1.getId());
		
		// get 
		Material actual = registrationDAO.getMaterialByCode("PET"); 
		assertEquals(material1.getId(), actual.getId());
		assertEquals(material1.getIsRecyclable(), actual.getIsRecyclable());
		assertEquals(material1.getName(), actual.getName());
		assertEquals(material1.getRecycleCode(), actual.getRecycleCode());
	}
	
	@Order(3)
	@Test
	public void testProductCrudOperations() {
		// insert
		registrationDAO.addProduct(product);
		
		/*// Will be tested with registration
		for (Material mat : product.getMaterials()) {
			registrationDAO.addProductMaterialMapping(product.getId(), mat.getId());
		}*/
		
		assertNotNull(product.getId());
		
		// get
		Product actual = registrationDAO.getProductById(product.getId()); 
		assertEquals(product.getBarCode(), actual.getBarCode());
		assertEquals(product.getSrNo(), actual.getSrNo());
		assertEquals(product.getContent(), actual.getContent());
		assertEquals(product.getName(), actual.getName());
		assertEquals(product.getSeller().getId(), actual.getSeller().getId());
		//assertEquals(product.getMaterials().size(),actual.getMaterials().size());
		
	}
	
	@Test
	@Order(4)
	public void testRegistrationCrudOperation(){
		//insert
		registrationDAO.addRegistration(registration);
		assertNotNull(registration.getId());
		
		
		//get 
		Registration newR = registrationDAO.getRegistrationById(registration.getId()); 
		assertEquals(registration.getId(), newR.getId());
		assertEquals(registration.getComments(), newR.getComments());
		assertEquals(registration.getProduct().getId(), newR.getProduct().getId());
		//assertEquals(registration.getRegistrationDate(), newR.getRegistrationDate());
		assertEquals(registration.getStatus(), newR.getStatus());
		//assertEquals(registration.getUpdateDate(), newR.getUpdateDate());
		assertEquals(registration.getUser(), newR.getUser());
		assertNotNull(newR.getRegistrationDate());
		assertNotNull(newR.getUpdateDate());
		
		/*// not yet implemented
		newR = registrationDAO.getRegistrationByProductBarCode(registration.getProduct().getBarCode());
		assertEquals(registration.getId(), newR.getId());
		assertEquals(registration.getComments(), newR.getComments());
		assertEquals(registration.getProduct().getId(), newR.getProduct().getId());
		//assertEquals(registration.getRegistrationDate(), newR.getRegistrationDate());
		assertEquals(registration.getStatus(), newR.getStatus());
		//assertEquals(registration.getUpdateDate(), newR.getUpdateDate());
		assertEquals(registration.getUser(), newR.getUser());
		assertNotNull(newR.getRegistrationDate());
		assertNotNull(newR.getUpdateDate());
		
		newR = registrationDAO.getRegistrationByProductSrNo(registration.getProduct().getSrNo());
		assertEquals(registration.getId(), newR.getId());
		assertEquals(registration.getComments(), newR.getComments());
		assertEquals(registration.getProduct().getId(), newR.getProduct().getId());
		//assertEquals(registration.getRegistrationDate(), newR.getRegistrationDate());
		assertEquals(registration.getStatus(), newR.getStatus());
		//assertEquals(registration.getUpdateDate(), newR.getUpdateDate());
		assertEquals(registration.getUser(), newR.getUser());
		assertNotNull(newR.getRegistrationDate());
		assertNotNull(newR.getUpdateDate());
		*/
	}
	
}
