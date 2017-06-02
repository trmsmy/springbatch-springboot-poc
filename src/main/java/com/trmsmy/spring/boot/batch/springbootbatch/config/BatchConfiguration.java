package com.trmsmy.spring.boot.batch.springbootbatch.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.trmsmy.spring.boot.batch.springbootbatch.ChunkExecutionNotificationListener;
import com.trmsmy.spring.boot.batch.springbootbatch.JobCompletionNotificationListener;
import com.trmsmy.spring.boot.batch.springbootbatch.MultiLineRecordItemProcessor;
import com.trmsmy.spring.boot.batch.springbootbatch.MultiLineTradeItemReader;
import com.trmsmy.spring.boot.batch.springbootbatch.StepExecutionNotificationListener;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.trmsmy.spring.boot.batch.springbootbatch")
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Autowired
	StepExecutionNotificationListener listener;

	@Autowired
	ChunkExecutionNotificationListener chunkListener;

	// tag::readerwriterprocessor[]
/*	@Bean
	public FlatFileItemReader<Person> reader() {
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		reader.setResource(new ClassPathResource("sample-data.csv"));
		reader.setLineMapper(new DefaultLineMapper<Person>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "firstName", "lastName" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						setTargetType(Person.class);
					}
				});
			}
		});
		return reader;
	}*/
	
	@Bean
	public MultiLineTradeItemReader reader() {
		MultiLineTradeItemReader reader = new MultiLineTradeItemReader();
		FlatFileItemReader<String> fileReader = new FlatFileItemReader<String>();
		fileReader.setResource(new ClassPathResource("sample-data.csv"));
		fileReader.setLineMapper(new DefaultLineMapper<String>());
		fileReader.setLineMapper(new PassThroughLineMapper());
		reader.setDelegate(fileReader);
		
		return reader;
		
	}

	@Bean
	public MultiLineRecordItemProcessor processor() {
		return new MultiLineRecordItemProcessor();
	}

	

/*	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}*/

	/*
	 * @Bean public JdbcBatchItemWriter<Person> writer() {
	 * JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
	 * writer.setItemSqlParameterSourceProvider(new
	 * BeanPropertyItemSqlParameterSourceProvider<Person>()); writer.
	 * setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)"
	 * ); writer.setDataSource(dataSource); return writer; } //
	 * end::readerwriterprocessor[]
	 */

	@Bean
	public ItemWriter<String> writer() {
		FlatFileItemWriter<String> writer = new FlatFileItemWriter<String>();
		writer.setResource(new FileSystemResource("target/output.txt"));
		writer.setLineAggregator(new PassThroughLineAggregator<>());
		return writer;
	}

	// tag::jobstep[]
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1()).end().build();

	}

	public Step step1() {
		return stepBuilderFactory.get("step1").<String, String>chunk(1).reader(reader()).processor(processor())
				.writer(writer()).build();
		//.listener(listener).listener(chunkListener)
	}
	// end::jobstep[]

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

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

}
