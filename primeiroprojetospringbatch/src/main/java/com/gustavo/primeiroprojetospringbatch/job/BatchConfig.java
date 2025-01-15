package com.gustavo.primeiroprojetospringbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {
	
	public final JobRepository jobRepository;
		
	public BatchConfig(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
	
	@Bean
	public Job imprimeOlaJob(Step imprimeOlaStep) {
		return new JobBuilder("imprimeOlaJob", jobRepository)
				.start(imprimeOlaStep)
				// Associa um JobParametersIncrementer ao Job para garantir parâmetros únicos a cada execução.
	    		// RunIdIncrementer: Incrementa automaticamente o parâmetro run.id.
				.incrementer(new RunIdIncrementer())
				.build();
	}

}
