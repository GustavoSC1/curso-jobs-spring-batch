package com.gustavo.parimparjob;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Configuration
public class ParImparBatchConfig {
	
	public final JobRepository jobRepository;
	public final JdbcTransactionManager transactionManager;
		
	public ParImparBatchConfig(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}
	
	@Bean
	public Job imprimeParImparJob() {
		return new JobBuilder("imprimeParImparJob", jobRepository)
				.start(imprimeParImparStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
	
	@Bean
	public Step imprimeParImparStep() {
		return new StepBuilder("imprimeParImparStep", jobRepository)
				.<Integer, String>chunk(1, transactionManager)
				.reader(contaAteDezReader())
				.processor(parOuImparProcessor())
				.writer(imprimeWriter())
				.build();
	}
	
	@Bean
	// Leitor simples utilizado para iterar sobre uma coleção ou um conjunto de dados pré-carregados.
	public IteratorItemReader<Integer> contaAteDezReader() {
		List<Integer> numerosDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		return new IteratorItemReader<Integer>(numerosDeUmAteDez.iterator());
	}
	
	@Bean
	// Implementação simples da interface ItemProcessor, projetada para aplicar uma função de transformação a cada item processado.
	public FunctionItemProcessor<Integer, String> parOuImparProcessor() {
		return new FunctionItemProcessor<Integer, String>
		(item -> item % 2 == 0 ? String.format("Item %s é Par", item) : String.format("Item %s é Impar", item));
	}
	
	@Bean
	public ItemWriter<String> imprimeWriter() {
		return itens -> itens.forEach(System.out::println);
	}

}
