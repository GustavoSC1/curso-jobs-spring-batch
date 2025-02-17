package com.gustavo.enviopromocoesemail.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;

import com.gustavo.enviopromocoesemail.dominio.InteresseProdutoCliente;

@Configuration
public class EnvioEmailClientesStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step enviaEmailClientesStep(
			ItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader,
			ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> processarEmailProdutoClienteProcessor,
			ItemWriter<SimpleMailMessage> enviaEmailProdutoClienteWriter) {
		return new StepBuilder("enviaEmailClientesStep", jobRepository)
				.<InteresseProdutoCliente, SimpleMailMessage>chunk(1, platformTransactionManager)
				.reader(lerInteresseProdutoClienteReader)
				.processor(processarEmailProdutoClienteProcessor)
				.writer(enviaEmailProdutoClienteWriter)
				.build();
	}

}
