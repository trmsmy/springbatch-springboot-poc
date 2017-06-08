package com.trmsmy.spring.boot.batch.springbootbatch.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration(exclude = { BatchAutoConfiguration.class, DataSourceAutoConfiguration.class,
		WebMvcAutoConfiguration.class })
@Import(MainConfiguration.class)
// @ImportResource("classpath:spring/config/job-context.xml")
public class SpringbootBatchApplication extends SpringBootServletInitializer {

	private static final String CONFIG = "classpath:spring/config/job-context.xml";

	/*
	 * @Autowired Job myjob;
	 * 
	 * @Autowired JobLauncher jobLauncher;
	 */

	public static void main(String[] args) throws Exception {

		System.setProperty("hawtio.authenticationEnabled", "false");

		SpringApplication.run(SpringbootBatchApplication.class, args);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG);

		Job myjob = applicationContext.getBean(Job.class);
		JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
		// new SpringbootBatchApplication().runJob();

		jobLauncher.run(myjob, new JobParameters());

	}

	/*
	 * void runJob() throws Exception { this.jobLauncher.run(myjob, new
	 * JobParameters()); }
	 */
/*
	@Bean
	public DataSource dataSource() throws SQLException {

		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScript("classpath:schema-all.sql")
				.build();

	}
*/
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringbootBatchApplication.class);
	}
}
