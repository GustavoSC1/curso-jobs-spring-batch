package com.gustavo.faturacartaocredito.step;

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

import com.gustavo.faturacartaocredito.dominio.FaturaCartaoCredito;

@Configuration
public class FaturaCartaoCreditoStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step faturaCartaoCreditoStep(
			ItemReader<FaturaCartaoCredito> lerTransacoesReader,
			ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregarDadosClienteProcessor,
			ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito) {
		return new StepBuilder("faturaCartaoCreditoStep", jobRepository)
				.<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1, platformTransactionManager)
				.reader(lerTransacoesReader)
				.processor(carregarDadosClienteProcessor)
				.writer(escreverFaturaCartaoCredito)
				.build();
	}

}
