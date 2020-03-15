package com.circ.microservices.registration.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class RegistrationDatabaseConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationDatabaseConfig.class); 
	
	
	
	// Create the Data Source using the configuration defined in application.yml 
	@Bean(name="dbRegistration")
	@Profile("default")
	@ConfigurationProperties(prefix="spring.registrationdb")
	public DataSource createRegistrationDataSource() {
		logger.info("Registering default DataSource");
		return DataSourceBuilder.create().build();
	}
	
	
	// Create the Data Source using the configuration defined in application.yml 
	@Bean(name="dbRegistration")
	@ConfigurationProperties(prefix="spring.registrationdb")
	@Profile("test")
	public DataSource createRegistrationDataSourceTest() {
		logger.info("Registering Test Datasource");
		return new EmbeddedDatabaseBuilder()
	              .setType(EmbeddedDatabaseType.H2)
	              .addScript("DB_SCHEMA.sql")
	              .build();
	}
	
	
	// Create a JDBC template object using the datasource 
	
	@Bean(name="jdbcRegistrationService")
	@Autowired 
	public JdbcTemplate createJdbcTemplateForRegistrationService(
			@Qualifier("dbRegistration") DataSource registrationDS) {
		return new JdbcTemplate(registrationDS);
	}
	

	
	// You can IoC JdbcTemplate now with the qualifier jdbcRegistraitonService 

}
