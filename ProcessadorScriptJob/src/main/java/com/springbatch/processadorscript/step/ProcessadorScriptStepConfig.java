package com.springbatch.processadorscript.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.processadorscript.dominio.Cliente;

@Configuration
public class ProcessadorScriptStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step processadorScriptStep(
			ItemReader<Cliente> processadorScriptReader,
			ItemProcessor<Cliente, Cliente> processadorScriptProcessor,
			ItemWriter<Cliente> processadorScriptWriter) {
		return new StepBuilder("processadorScriptStep", jobRepository)
				.<Cliente, Cliente>chunk(1, platformTransactionManager)
				.reader(processadorScriptReader)
				.processor(processadorScriptProcessor)
				.writer(processadorScriptWriter)
				.build();
	}
}
