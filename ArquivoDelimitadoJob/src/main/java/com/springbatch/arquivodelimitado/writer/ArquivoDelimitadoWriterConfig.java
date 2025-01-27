package com.springbatch.arquivodelimitado.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.arquivodelimitado.dominio.Cliente;

@Configuration
public class ArquivoDelimitadoWriterConfig {
	
	@Bean
	@StepScope
	public FlatFileItemWriter<Cliente> leituraArquivoDelimitadoWriter(
			@Value("#{jobParameters['arquivoClientesSaida']}") String arquivoClientesSaida) {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoLarguraFixaWriter")
				.resource(new FileSystemResource(arquivoClientesSaida))
				.delimited()
				.delimiter(",")
				.names(new String[] {"nome", "sobrenome", "idade", "email"})
				.build();
	}
	
}
