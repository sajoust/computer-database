package com.excilys.formation.CDB.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.formation.CDB.connection","com.excilys.formation.CDB.persistence","com.excilys.formation.CDB.service","com.excilys.formation.CDB.servlets"})
public class SpringConfig extends AbstractContextLoaderInitializer {
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(SpringConfig.class);
		return applicationContext;
	}
	

	
	@Bean
	public HikariDataSource hikariDataSource() {
		return new HikariDataSource(new HikariConfig("/connector.properties"));
	}
	
}
