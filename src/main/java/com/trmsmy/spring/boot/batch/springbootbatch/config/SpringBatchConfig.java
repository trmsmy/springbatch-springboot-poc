package com.trmsmy.spring.boot.batch.springbootbatch.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class SpringBatchConfig {

	@Bean
	public DataSource dataSource() throws SQLException {

		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScript("classpath:schema-all.sql")
				.build();
		/*
		 * final DriverManagerDataSource dataSource = new
		 * DriverManagerDataSource();
		 * dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		 * dataSource.setUrl("jdbc:hsqldb:mem:test");
		 * dataSource.setUsername("sa"); dataSource.setPassword(""); dataSource.
		 * return dataSource;
		 */ }
}
