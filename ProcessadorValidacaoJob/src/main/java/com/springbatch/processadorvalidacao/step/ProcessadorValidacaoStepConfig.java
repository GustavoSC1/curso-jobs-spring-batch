package com.springbatch.processadorvalidacao.step;

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

import com.springbatch.processadorvalidacao.dominio.Cliente;

@Configuration
public class ProcessadorValidacaoStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;	
	
	@Bean
	public Step processadorValidacaoStep(
			ItemReader<Cliente> processadorValidacaoReader,
			ItemProcessor<Cliente, Cliente> processadorValidacaoProcessor,
			ItemWriter<Cliente> processadorValidacaoWriter) {
		return new StepBuilder("processadorValidacaoStep", jobRepository)
				.<Cliente, Cliente>chunk(1, platformTransactionManager)
				.reader(processadorValidacaoReader)
				.processor(processadorValidacaoProcessor)
				.writer(processadorValidacaoWriter)
				.build();
	}
}
