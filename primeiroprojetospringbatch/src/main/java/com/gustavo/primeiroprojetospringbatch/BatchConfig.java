package com.gustavo.primeiroprojetospringbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Configuration
public class BatchConfig {
	
	public final JobRepository jobRepository;
	public final JdbcTransactionManager transactionManager;
		
	public BatchConfig(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}
	
	@Bean
	public Job imprimeOlaJob() {
		return new JobBuilder("imprimeOlaJob", jobRepository)
				.start(imprimeOlaStep())
				.build();
	}
	
	@Bean
	public Step imprimeOlaStep() {
		return new StepBuilder("imprimeOlaStep", jobRepository)
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("Olá, mundo!");
						return RepeatStatus.FINISHED;
					}
				}, transactionManager)
				.build();
	}

}
