package com.example.migracaodados.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class MigracaoDadosJobConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Bean
	public Job migracaoDadosJob(
			@Qualifier("migrarPessoaStep") Step migrarPessoaStep,
			@Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep) {
		return new JobBuilder("migracaoDadosJob", jobRepository)
				.start(stepsParalelos(migrarPessoaStep, migrarDadosBancariosStep))
				.end()
				.incrementer(new RunIdIncrementer())
				.build();
	}
	
	// Permite executar dois steps de forma paralela (concorrente) ao invés de sequencialmente.
	private Flow stepsParalelos(Step migrarPessoaStep, Step migrarDadosBancariosStep) {
		Flow migrarDadosBancariosFlow = new FlowBuilder<Flow>("migrarDadosBancariosFlow")
				.start(migrarDadosBancariosStep)
				.build();
		
		Flow stepsParalelos = new FlowBuilder<Flow>("stepsParalelosFlow")
				.start(migrarPessoaStep)
				// split() permite dividir o fluxo em múltiplos sub-fluxos que serão executados em paralelo.
				// SimpleAsyncTaskExecutor gerencia a execução concorrente desses sub-fluxos.
				.split(new SimpleAsyncTaskExecutor())
				.add(migrarDadosBancariosFlow)
				.build();
		
		return stepsParalelos;
	}

}
