package com.springbatch.skipexception.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.skipexception.dominio.Cliente;

@Configuration
public class SkipExceptionStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step skipExceptionStep(ItemReader<Cliente> skipExceptionReader, ItemWriter<Cliente> skipExceptionWriter) {
		return new StepBuilder("skipExceptionStep", jobRepository)
				.<Cliente, Cliente>chunk(11, platformTransactionManager)
				.reader(skipExceptionReader)
				.writer(skipExceptionWriter)
				// Habilita a tolerância a falhas no Step
				.faultTolerant()
				// Especifica a Exception como uma exceção ignorável
				.skip(Exception.class)
				.skipLimit(2)
				.build();
	}
}
