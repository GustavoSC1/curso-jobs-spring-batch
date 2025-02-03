package com.springbatch.contasbancarias.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
			ClassifierCompositeItemWriter<Conta> classifierContaWriter,
			@Qualifier("fileContaWriter")FlatFileItemWriter<Conta> clienteValidosWriter,
			@Qualifier("clienteInvalidoWriter")FlatFileItemWriter<Conta> clienteInvalidoWriter) {
		return new StepBuilder("criacaoContasStep", jobRepository)
				.<Cliente, Conta>chunk(100, platformTransactionManager)
				.reader(leituraClientesReader)
				.processor(geracaoContaProcessor)
				.writer(classifierContaWriter)
				// Serve para registrar componentes que implementam a interface ItemStream dentro de um Step, garantindo que esses 
				// componentes sejam abertos, atualizados e fechados corretamente durante a execução do job.
				.stream(clienteValidosWriter)
				.stream(clienteInvalidoWriter)
				.build();
	}
}
