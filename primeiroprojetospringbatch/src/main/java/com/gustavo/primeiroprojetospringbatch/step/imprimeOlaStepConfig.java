package com.gustavo.primeiroprojetospringbatch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Configuration
public class imprimeOlaStepConfig {
	
	public final JobRepository jobRepository;
	public final JdbcTransactionManager transactionManager;
		
	public imprimeOlaStepConfig(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}
	
	@Bean
	public Step imprimeOlaStep(Tasklet imprimeOlaTasklet) {
		return new StepBuilder("imprimeOlaStep", jobRepository)
				.tasklet(imprimeOlaTasklet, transactionManager)
				.build();
	}	

}
