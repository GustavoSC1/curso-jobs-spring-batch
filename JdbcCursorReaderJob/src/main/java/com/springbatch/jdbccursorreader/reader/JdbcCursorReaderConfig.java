package com.springbatch.jdbccursorreader.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.springbatch.jdbccursorreader.dominio.Cliente;

@Configuration
public class JdbcCursorReaderConfig {
	
	@Bean
	public JdbcCursorItemReader<Cliente> jdbcCursorReader(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder()
					.name("jdbcCursorReader")
					.dataSource(dataSource)
					.sql("select * from cliente")
					// Mapeia as colunas do ResultSet para os atributos de um objeto Java 
					// usando nomes de propriedades correspondentes.
					.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
					.build();					
	}
	
}
