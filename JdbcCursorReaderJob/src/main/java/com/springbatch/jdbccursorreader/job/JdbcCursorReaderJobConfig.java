package com.springbatch.jdbccursorreader.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcCursorReaderJobConfig {
	
	@Autowired
	public JobRepository jobRepository;
		
	@Bean
	public Job jdbcCursorReaderJob(Step jdbcCursorReaderStep) {
		return new JobBuilder("jdbcCursorReaderJob", jobRepository)
				.start(jdbcCursorReaderStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
