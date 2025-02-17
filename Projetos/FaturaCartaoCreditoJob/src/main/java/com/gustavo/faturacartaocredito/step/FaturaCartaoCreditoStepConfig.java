package com.gustavo.faturacartaocredito.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.gustavo.faturacartaocredito.dominio.FaturaCartaoCredito;
import com.gustavo.faturacartaocredito.reader.FaturaCartaoCreditoReader;
import com.gustavo.faturacartaocredito.writer.TotalTransacoesFooterCallback;
import com.gustavo.faturacartaocredito.dominio.Transacao;

@Configuration
public class FaturaCartaoCreditoStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step faturaCartaoCreditoStep(
			ItemStreamReader<Transacao> lerTransacoesReader,
			ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregarDadosClienteProcessor,
			ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito,
			TotalTransacoesFooterCallback listener) {
		return new StepBuilder("faturaCartaoCreditoStep", jobRepository)
				.<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1, platformTransactionManager)
				.reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
				.processor(carregarDadosClienteProcessor)
				.writer(escreverFaturaCartaoCredito)
				.listener(listener)
				.build();
	}

}
