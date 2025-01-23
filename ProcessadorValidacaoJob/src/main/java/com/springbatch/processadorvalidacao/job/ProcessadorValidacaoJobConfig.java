package com.springbatch.processadorvalidacao.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorValidacaoJobConfig {
	
	@Autowired
	public JobRepository jobRepository;
		
	@Bean
	public Job processadorValidacaoJob(Step processadorValidacaoStep) {
		return new JobBuilder("processadorValidacaoJob", jobRepository)
				.start(processadorValidacaoStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
