package com.springbatch.jdbccursorreader.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.jdbccursorreader.dominio.Cliente;

@Configuration
public class JdbcCursorReaderStepConfig {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
		
	@Bean
	public Step jdbcCursorReaderStep(ItemReader<Cliente> jdbcCursorReader, ItemWriter<Cliente> jdbcCursorWriter) {
		return new StepBuilder("jdbcCursorReaderStep", jobRepository)
				.<Cliente, Cliente>chunk(1, platformTransactionManager)
				.reader(jdbcCursorReader)
				.writer(jdbcCursorWriter)
				.build();
	}
}
