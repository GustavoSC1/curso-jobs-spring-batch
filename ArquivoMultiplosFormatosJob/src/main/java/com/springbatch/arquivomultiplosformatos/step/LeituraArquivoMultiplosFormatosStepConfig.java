package com.springbatch.arquivomultiplosformatos.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;

@Configuration
public class LeituraArquivoMultiplosFormatosStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;

	@Bean
	public Step leituraArquivoMultiplosFormatosStep(
			MultiResourceItemReader<Cliente> multiplosArquivosClienteTransacaoReader,
			ItemWriter leituraArquivoMultiplosFormatosItemWriter) {
		return new StepBuilder("leituraArquivoMultiplosFormatosStep",jobRepository)
				.chunk(1, platformTransactionManager)
				.reader(multiplosArquivosClienteTransacaoReader)
				.writer(leituraArquivoMultiplosFormatosItemWriter)
				.build();
	}
}
