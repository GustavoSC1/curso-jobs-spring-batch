package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

@Configuration
public class CompositeContaWriterConfig {
	
	@Bean
	// Permite encadear múltiplos ItemWriters para que todos sejam executados ao mesmo tempo.
	public CompositeItemWriter<Conta> compositeConttaWriter(
			FlatFileItemWriter<Conta> flatFileItemWriter,
			JdbcBatchItemWriter<Conta> jdbcBatchItemWriter){
		return new CompositeItemWriterBuilder<Conta>()
				.delegates(flatFileItemWriter, jdbcBatchItemWriter)
				.build();
	}

}
