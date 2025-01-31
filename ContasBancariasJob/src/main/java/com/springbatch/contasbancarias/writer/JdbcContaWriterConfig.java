package com.springbatch.contasbancarias.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

@Configuration
public class JdbcContaWriterConfig {
	
	@Bean
	// Escreve lotes de dados diretamente em um banco de dados usando JDBC. Altamente eficiente porque executa inserções 
	// ou atualizações em batch, reduzindo o número de chamadas ao banco de dados e melhorando o desempenho.
	public JdbcBatchItemWriter<Conta> jdbcContaWriter(@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Conta>()
				.dataSource(dataSource)
				.sql("INSERT INTO conta (tipo, limite, cliente_id) VALUES (?, ?, ?)")
				// Permite o mapeamento manual dos valores dos objetos Java para os parâmetros da query SQL.
				.itemPreparedStatementSetter(itemPreparedStatementSetter())
				.build();
	}
	
	private ItemPreparedStatementSetter<Conta> itemPreparedStatementSetter() {
		return new ItemPreparedStatementSetter<Conta>() {

			@Override
			public void setValues(Conta conta, PreparedStatement ps) throws SQLException {
				ps.setString(1, conta.getTipo().name());
				ps.setDouble(2, conta.getLimite());
				ps.setString(3, conta.getClienteId());
			}
			
		};
	}
	
}
