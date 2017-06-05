package com.trmsmy.spring.boot.batch.springbootbatch.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
public class SpringbootBatchApplication {

	private static final String CONFIG = "/META-INF/spring/config/job-context.xml";

	public static void main(String[] args) throws Exception {
		
		System.setProperty("hawtio.authenticationEnabled", "false");
		
		SpringApplication.run(SpringbootBatchApplication.class, args);

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG);

		Job job = applicationContext.getBean(Job.class);
		JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);

		jobLauncher.run(job, new JobParameters());

	}
	
	
	@Bean
	public DataSource dataSource() throws SQLException {

		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:schema-all.sql")
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
