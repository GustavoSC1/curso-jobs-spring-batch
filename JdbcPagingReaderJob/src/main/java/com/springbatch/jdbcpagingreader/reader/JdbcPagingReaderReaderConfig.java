package com.springbatch.jdbcpagingreader.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.springbatch.jdbcpagingreader.dominio.Cliente;

@Configuration
public class JdbcPagingReaderReaderConfig {
	
	@Bean
	// O JdbcPagingItemReader lê os dados do banco de forma paginada, carregando um número fixo de registros por vez (página).
	// Recomendado para trabalhar com grandes conjuntos de dados ou em cenários onde a memória e a carga no banco precisam ser 
	// controladas.
	public JdbcPagingItemReader<Cliente> jdbcPagingReader(
			@Qualifier("appDataSource") DataSource dataSource,
			PagingQueryProvider pagingQueryProvider) {
		return new JdbcPagingItemReaderBuilder<Cliente>()
				.name("jdbcPagingReader")
				.dataSource(dataSource)
				.queryProvider(pagingQueryProvider)
				.pageSize(1)
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
				.build();			
	}
	
	@Bean
	// Usada para criar um PagingQueryProvider, que é uma abstração responsável por gerar as consultas SQL 
	// que recuperam os dados de forma paginada.
	public SqlPagingQueryProviderFactoryBean queryProvider(
			@Qualifier("appDataSource") DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("select *");
		queryProvider.setFromClause("from cliente");
		// Coluna usada para ordenação (necessária para paginação)
		// Garante que em caso de reinicialização ele consiga trazer os resultados na mesma ordem
		queryProvider.setSortKey("email");
		
		return queryProvider;
	}
	
}
