package com.springbatch.arquivolargurafixa.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class ArquivoLarguraFixaWriterConfig {
	
	@Bean
	@StepScope
	public FlatFileItemWriter<Cliente> escritaArquivoLarguraFixaWriter(
			@Value("#{jobParameters['arquivoClientesSaida']}") String arquivoClientesSaida) {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoLarguraFixaWriter")
				.resource(new FileSystemResource(arquivoClientesSaida))
				.formatted()
				.format("%-9s %-9s %-2s %-19s")
				.names(new String[] {"nome", "sobrenome", "idade", "email"})
				.build();
	}
	
}
