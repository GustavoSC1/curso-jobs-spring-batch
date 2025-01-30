package com.springbatch.demonstrativoorcamentario.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.reader.GrupoLancamentoReader;
import com.springbatch.demonstrativoorcamentario.writer.DemonstrativoOrcamentarioRodape;

@Configuration
public class DemonstrativoOrcamentarioStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Bean
	public Step demonstrativoOrcamentarioStep(
			// Esse aqui lê dos arquivos
			//MultiResourceItemReader<GrupoLancamento> demonstrativoOrcamentarioReader,
			// Esse aqui lê do banco de dados
			GrupoLancamentoReader demonstrativoOrcamentarioReader,
			MultiResourceItemWriter<GrupoLancamento> multiDemonstrativoOrcamentarioWriter,
			DemonstrativoOrcamentarioRodape rodapeCallback) {
		return new StepBuilder("demonstrativoOrcamentarioStep", jobRepository)
				.<GrupoLancamento,GrupoLancamento>chunk(1, platformTransactionManager)
				.reader(demonstrativoOrcamentarioReader)
				.writer(multiDemonstrativoOrcamentarioWriter)
				// Adiciona um listener ao Step para executar lógica personalizada durante a execução do job
				// Obs: @BeforeWrite é um listener no Spring Batch
				.listener(rodapeCallback)
				.build();
	}
}
