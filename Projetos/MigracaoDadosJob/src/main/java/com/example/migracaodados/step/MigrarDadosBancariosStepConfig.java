package com.example.migracaodados.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.migracaodados.dominio.DadosBancarios;
import com.example.migracaodados.dominio.Pessoa;

@Configuration
public class MigrarDadosBancariosStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step migrarDadosBancariosStep(
			ItemReader<DadosBancarios> arquivoDadosBancariosReader,
			ItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
		return new StepBuilder("migrarDadosBancariosStep", jobRepository)
				.<DadosBancarios, DadosBancarios>chunk(10000, platformTransactionManager)
				.reader(arquivoDadosBancariosReader)
				.writer(bancoDadosBancariosWriter)
				.build();
	}

}
