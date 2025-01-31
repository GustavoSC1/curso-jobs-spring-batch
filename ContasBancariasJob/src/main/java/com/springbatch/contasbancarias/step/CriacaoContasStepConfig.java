package com.springbatch.contasbancarias.step;

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

import com.springbatch.contasbancarias.dominio.Cliente;
import com.springbatch.contasbancarias.dominio.Conta;

@Configuration
public class CriacaoContasStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
		
	@Bean
	public Step criacaoContasStep(
			ItemReader<Cliente> leituraClientesReader, 
			ItemProcessor<Cliente, Conta> geracaoContaProcessor,
			ItemWriter<Conta> impressaoContaWriter) {
		return new StepBuilder("criacaoContasStep", jobRepository)
				.<Cliente, Conta>chunk(100, platformTransactionManager)
				.reader(leituraClientesReader)
				.processor(geracaoContaProcessor)
				.writer(impressaoContaWriter)
				.build();
	}
}
