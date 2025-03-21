package com.example.migracaodados.writer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.migracaodados.dominio.Pessoa;

@Configuration
public class BancoPessoaWriterConfig {
	
	@Bean
	public JdbcBatchItemWriter<Pessoa> bancoPessoaWriter(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder()
				.dataSource(dataSource)
				.sql("INSERT INTO pessoa (id, nome, email, data_nascimento, idade) VALUES (?, ?, ?, ?, ?)")
				.itemPreparedStatementSetter(itemPreparedStatementSetter())
				.build();
	}
	
	// Define como os valores de um objeto serão mapeados para os parâmetros de um PreparedStatement antes de serem 
	// inseridos ou atualizados no banco de dados.
	private ItemPreparedStatementSetter<Pessoa> itemPreparedStatementSetter() {
		return new ItemPreparedStatementSetter<Pessoa>() {

			@Override
			public void setValues(Pessoa pessoa, PreparedStatement ps) throws SQLException {
				ps.setInt(1, pessoa.getId());
				ps.setString(2, pessoa.getNome());
				ps.setString(3, pessoa.getEmail());
				ps.setDate(4, new Date(pessoa.getDataNascimento().getTime()));
				ps.setInt(5, pessoa.getIdade());
			}
			
		};
	}

}
