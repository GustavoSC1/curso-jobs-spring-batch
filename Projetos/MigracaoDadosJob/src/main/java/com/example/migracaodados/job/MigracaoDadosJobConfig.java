package com.example.migracaodados.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigracaoDadosJobConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Bean
	public Job migracaoDadosJob(
			@Qualifier("migrarPessoaStep") Step migrarPessoaStep,
			@Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep) {
		return new JobBuilder("migracaoDadosJob", jobRepository)
				.start(migrarPessoaStep)
				.next(migrarDadosBancariosStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}

}
