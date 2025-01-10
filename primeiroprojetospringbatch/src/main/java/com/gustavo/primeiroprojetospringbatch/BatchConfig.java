package com.gustavo.primeiroprojetospringbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Configuration
public class BatchConfig {
	
	public final JobRepository jobRepository;
	public final JdbcTransactionManager transactionManager;
		
	public BatchConfig(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}
	
	@Bean
	public Job imprimeOlaJob() {
		return new JobBuilder("imprimeOlaJob", jobRepository)
				.start(imprimeOlaStep())
				// Associa um JobParametersIncrementer ao Job para garantir parâmetros únicos a cada execução.
	    		// RunIdIncrementer: Incrementa automaticamente o parâmetro run.id.
				.incrementer(new RunIdIncrementer())
				.build();
	}
	
	@Bean
	public Step imprimeOlaStep() {
		return new StepBuilder("imprimeOlaStep", jobRepository)
				.tasklet(imprimeOlaTasklet(null), transactionManager)
				.build();
	}
	
	@Bean
	// Permite que o bean seja criado apenas quando o Step associado estiver em execução e que seja isolado para essa execução.
	// Evitando a criação desnecessária de objetos durante a inicialização da aplicação.
	// Isso é particularmente útil em cenários onde o bean depende de parâmetros dinâmicos que são passados no momento da execução do job.
	@StepScope
	public Tasklet imprimeOlaTasklet(@Value("#{jobParameters['nome']}") String nome) {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println(String.format("Olá, %s!", nome));
				return RepeatStatus.FINISHED;
			}
		};
	}

}
