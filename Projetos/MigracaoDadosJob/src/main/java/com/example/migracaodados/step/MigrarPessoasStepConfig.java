package com.example.migracaodados.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.migracaodados.dominio.Pessoa;

@Configuration
public class MigrarPessoasStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step migrarPessoaStep(
			ItemReader<Pessoa> arquivoPessoaReader,
			ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter,
			FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWriter) {
		return new StepBuilder("migrarPessoaStep", jobRepository)
				.<Pessoa, Pessoa>chunk(1, platformTransactionManager)
				.reader(arquivoPessoaReader)
				.writer(pessoaClassifierWriter)
				.stream(arquivoPessoasInvalidasWriter)
				.build();
	}

}
