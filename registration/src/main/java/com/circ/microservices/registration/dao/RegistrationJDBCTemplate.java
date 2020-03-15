package com.circ.microservices.registration.dao;

import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.circ.microservices.registration.exception.MaterialsNotFoundException;
import com.circ.microservices.registration.exception.ProductNotFoundException;
import com.circ.microservices.registration.exception.RegistrationException;
import com.circ.microservices.registration.model.Material;
import com.circ.microservices.registration.model.Product;
import com.circ.microservices.registration.model.Registration;
import com.circ.microservices.registration.model.Seller;
import com.circ.microservices.registration.model.mapper.MaterialMapper;
import com.circ.microservices.registration.model.mapper.ProductMapper;
import com.circ.microservices.registration.model.mapper.RegistrationMapper;
import com.circ.microservices.registration.model.mapper.SellerMapper;

@Component
public class RegistrationJDBCTemplate implements RegistrationDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationJDBCTemplate.class);
	
	@Qualifier("jdbcRegistrationService")
	@Autowired
	private JdbcTemplate jdbcTemplate;


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Registration getRegistrationById(Integer id) throws RegistrationException{
		try {
			String sql = "SELECT * FROM REGISTRATION WHERE REG_ID=?";
			logger.info("EXECUTING query " + sql);
			Registration registration = jdbcTemplate.queryForObject(sql, new Object[] { id },
					new RegistrationMapper(this));
			logger.info("FINISHED " + sql);
			if (registration != null)
				logger.info("Registration found for id " + id);
			else {
				logger.error("No registration found for id " + id);
				throw new RegistrationException("No registrations found for id " + id);
			}
			return registration;
		} catch (Exception e) {
			logger.error("No registration found for id " + id, e);
			throw new RegistrationException("No registrations found for id " + id);
		}
	}

	@Override
	public Registration getRegistrationByProductSrNo(Long srNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registration getRegistrationByProductBarCode(Long barCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registration> getAllRegistrationsForSeller(Integer sellerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registration> getAllRegistrationsForMaterial(Integer materialId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void addRegistration(Registration registration) throws RegistrationException{
		// Lets first add the seller if seller does not exist
		Seller seller = registration.getProduct().getSeller(); 
		Seller sellerCheck = getSellerByLEI(seller.getLei());
		
		//TODO: Transaction Management
		
		if (sellerCheck == null) {
			addSeller(seller);
		} else {
			seller.setId(sellerCheck.getId());
		}
		
		// Next add Product 
		Product product = registration.getProduct();
		Product productCheck = getProductBySrNo(product.getSrNo());
		
		if (productCheck == null) {
			addProduct(product);
		} else {
			product.setId(productCheck.getId());
		}
		
		// Now Lets add all the Product Materials 
		List<Material> materials = product.getMaterials();
		for (Material material : materials) {
			Material materialCheck = getMaterialByCode(material.getCode());
			
			if (materialCheck == null) { //No Material exists with given code. Add one
				addPackagingMaterial(material);
			} else {
				material.setId(materialCheck.getId());
			}
			
			// Add ProductMaterialMapping 
			addProductMaterialMapping(product.getId(), material.getId());
		}
		
		// Now lets add the registration 
		
		String sql = "insert into REGISTRATION (REG_DATE, REG_UPDATE_DATE, REG_STATUS, REG_USER, REG_COMMENTS, PRD_ID) VALUES (NOW(),NOW(),?,?,?,?)" ;
		
		logger.info("EXECUTING query "+sql);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.INTEGER));
		factory.setReturnGeneratedKeys(true);
		
		Object[] parameters = new Object[] {registration.getStatus(), registration.getUser(), registration.getComments(), product.getId()};
		
		jdbcTemplate.update(factory.newPreparedStatementCreator(parameters), keyHolder);
		registration.setId(new Integer(keyHolder.getKey().intValue()));
		
		logger.info("Registered Product with id "+registration.getId());
		
		// Registration is now complete 

	}
		
		

	

	@Override
	public void addProduct(Product p) {
		String sql = "INSERT INTO PRODUCT (PRD_SERIAL_NO, PRD_BAR_CODE, PRD_NAME, PRD_CONTENT, SLR_ID) VALUES (?,?,?,?,?) ";
		
		logger.info("EXECUTING query "+sql);
		logger.info("With parameters "+p);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
		factory.addParameter(new SqlParameter(Types.BIGINT));
		factory.addParameter(new SqlParameter(Types.BIGINT));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.INTEGER));
		factory.setReturnGeneratedKeys(true);
		
		Object[] parameters = new Object[] {p.getSrNo(), p.getBarCode(), p.getName(), p.getContent(), p.getSeller().getId()};
		
		jdbcTemplate.update(factory.newPreparedStatementCreator(parameters), keyHolder);
		p.setId(new Integer(keyHolder.getKey().intValue()));
		
		logger.info("Added Product with id "+p.getId());

	}

	@Override
	public Product getProductBySrNo(Long srNo) throws ProductNotFoundException{
		try {
			String sql = "SELECT P.*, S.SLR_NAME, S.SLR_LEI  from PRODUCT AS P, SELLER AS S where P.SLR_ID = S.SLR_ID and P.PRD_SERIAL_NO=?";
			String checkSql = "SELECT COUNT(PRD_ID) FROM PRODUCT WHERE PRD_SERIAL_NO=?";
			Object[] parameters = new Object[] { srNo }; 
			
			int count = jdbcTemplate.queryForObject(checkSql, Integer.class, parameters); 
			if (count > 0) {
				logger.info("EXECUTING query " + sql);
				Product product = jdbcTemplate.queryForObject(sql, parameters , new ProductMapper(this));
				logger.info("FINISHED " + sql);
				logger.info("Found product with id " + srNo);
				return product;
			} else {
				logger.error("No product found for id " + srNo);
				return null;
			}
			
		} catch (Exception e) {
			logger.error("Error occurred while getting Product with id "+srNo,e);
			throw new ProductNotFoundException("No product found for id " + srNo);
		}
	}
	

	@Override
	public void addPackagingMaterial(Material m) {
		String sql = "INSERT INTO MATERIAL (MTL_CODE, MTL_NAME, MTL_RECYCLABLE, MTL_RECYCLE_CODE, MTL_TYPE, MTL_SUB_TYPE_GROUP, MTL_SUB_TYPE) VALUES (?,?,?,?,?,?,?)";
		Object[] parameters = new Object[] {m.getCode(), m.getName(), m.getIsRecyclable(), m.getRecycleCode(), m.getType(), m.getSubTypeGroup(), m.getSubType()}; 
		
		logger.info("EXECUTING query "+sql);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.TINYINT));
		factory.addParameter(new SqlParameter(Types.TINYINT));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.setReturnGeneratedKeys(true);
		
		jdbcTemplate.update(factory.newPreparedStatementCreator(parameters), keyHolder);
		m.setId(new Integer(keyHolder.getKey().intValue()));
		
		logger.info("Added Material with id "+m.getId());

	}

	
	@Override
	public Material getMaterialByCode(String materialCode) throws MaterialsNotFoundException {
		try {
			String sql = "select M.* from MATERIAL M WHERE M.MTL_CODE = ?";
			String checkSql = "select COUNT(M.MTL_ID) from MATERIAL M WHERE M.MTL_CODE = ?";
			Object[] parameters = new Object[] { materialCode };
			
			int count = jdbcTemplate.queryForObject(checkSql, Integer.class, parameters); 
			
			if (count > 0) {
				logger.info("EXECUTING query " + sql);
				Material material = jdbcTemplate.queryForObject(sql, parameters , new MaterialMapper());
				logger.info("FINISHED " + sql);
				logger.info("Material found for id " + materialCode);
				return material; 
			} else return null;
			
			
		} catch (Exception e) {
			logger.error("No Materials found for id " + materialCode, e);
			throw new MaterialsNotFoundException("No Materials found for id " + materialCode);
		}
	}
	
	
	@Override
	public List<Material> getMaterials(Integer productId) {
		try {
			String sql = "select M.* from PRODUCT_MTL PM, MATERIAL M WHERE PM.MTL_ID = M.MTL_ID AND PM.PRD_ID = ?";
			logger.info("EXECUTING query " + sql);
			List<Material> materials = jdbcTemplate.query(sql, new Object[] { productId }, new MaterialMapper());
			logger.info("FINISHED " + sql);
			if (materials != null)
				logger.info(materials.size() + " materials found for product id " + productId);
			else {
				logger.error("No Materials found for product id " + productId);
				throw new MaterialsNotFoundException("No Materials found for product id " + productId);
			}
			return materials;
		} catch (Exception e) {
			logger.error("No Materials found for product id " + productId, e);
			throw new MaterialsNotFoundException("No Materials found for product id " + productId);
		}
	}

	@Override
	public void addSeller(Seller seller) throws RegistrationException {
		String sql = "INSERT INTO SELLER (SLR_NAME,SLR_LEI) VALUES (?,?)";
		
		logger.info("EXECUTING query "+sql);
		logger.info("With parameters "+seller.getName()+" and "+seller.getLei());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.addParameter(new SqlParameter(Types.VARCHAR));
		factory.setReturnGeneratedKeys(true);
		
		jdbcTemplate.update(factory.newPreparedStatementCreator(new Object[] {seller.getName(), seller.getLei()}), keyHolder);
		seller.setId(new Integer(keyHolder.getKey().intValue()));
		
		logger.info("Added Seller with id "+seller.getId());

	}

	@Override
	public Seller getSeller(Integer id) {
		String sql = "SELECT * FROM SELLER WHERE SLR_ID=?";
		Object[] params = {id};
		
		List<Seller> sellers =  jdbcTemplate.query(sql, params ,new SellerMapper());
		return sellers.get(0);
	}
	
	@Override 
	public Seller getSellerByLEI(String lei) {
		String checkSql = "SELECT COUNT(SLR_ID) FROM SELLER WHERE SLR_LEI=?";
		String sql = "SELECT * FROM SELLER WHERE SLR_LEI=?";
		Object[] parameters = new Object[] {lei};
		
		Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, parameters);
		if (count.intValue() > 0) {
		
			logger.info("EXECUTING query "+sql);
			logger.info("LEI parameter is "+lei);
			Seller seller = jdbcTemplate.queryForObject(sql,parameters,new SellerMapper());
			logger.info("FINISHED "+sql);
			
			return seller;
		} else {
			return null;
		}
	}

	@Override
	public List<Registration> getAllRegistrations() {
		
		try {
			String sql = "SELECT * FROM REGISTRATION";
			logger.info("EXECUTING query " + sql);
			List<Registration> registrations = jdbcTemplate.query(sql, new RegistrationMapper(this));
			logger.info("FINISHED " + sql);
			if (registrations != null)
				logger.info(registrations.size() + "Rows returned");
			else {
				logger.error("No registrations found in database.");
				throw new RegistrationException("No registrations found in database.");
			}
			return registrations;
		} catch (Exception e) {
			logger.error("Error occurred while getting all registrations",e);
			throw new RegistrationException("No registrations found in database.");
		}
	}

	@Override
	public Product getProductById(Integer id) throws ProductNotFoundException{
		
		try {
			String sql = "SELECT P.*, S.SLR_NAME, S.SLR_LEI  from PRODUCT P, SELLER S where P.SLR_ID = S.SLR_ID and P.PRD_ID=?";
			logger.info("EXECUTING query " + sql);
			Product product = jdbcTemplate.queryForObject(sql, new Object[] { id }, new ProductMapper(this));
			logger.info("FINISHED " + sql);
			if (product != null)
				logger.info("Found product with id " + id);
			else {
				logger.error("No product found for id " + id);
				throw new ProductNotFoundException("No product found for id " + id);
			}
			return product;
		} catch (Exception e) {
			logger.error("Error occurred while getting Product with id "+id,e);
			throw new ProductNotFoundException("No product found for id " + id);
		}
	}

	@Override
	public void addProductMaterialMapping(Integer productId, Integer materialId) {
		String sql = "INSERT INTO PRODUCT_MTL (PRD_ID, MTL_ID) VALUES (?,?) ";
		
		logger.info("EXECUTING query "+sql);
		logger.info("With parameters "+productId+ " and " + materialId);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
		factory.addParameter(new SqlParameter(Types.INTEGER));
		factory.addParameter(new SqlParameter(Types.INTEGER));
		factory.setReturnGeneratedKeys(true);
		
		Object[] parameters = new Object[] {productId, materialId};
		
		jdbcTemplate.update(factory.newPreparedStatementCreator(parameters), keyHolder);
		
		logger.info("Added Product Material mapping product id "+productId);
		
	}



}
