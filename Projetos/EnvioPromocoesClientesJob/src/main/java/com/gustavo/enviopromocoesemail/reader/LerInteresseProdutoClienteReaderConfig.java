package com.gustavo.enviopromocoesemail.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.gustavo.enviopromocoesemail.dominio.Cliente;
import com.gustavo.enviopromocoesemail.dominio.InteresseProdutoCliente;
import com.gustavo.enviopromocoesemail.dominio.Produto;

@Configuration
public class LerInteresseProdutoClienteReaderConfig {
	
	public JdbcCursorItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader(
			@Qualifier("appDataSource") DataSource dataSource){
		return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
				.name("lerInteresseProdutoClienteReader")
				.dataSource(dataSource)
				.sql("select * from interesse_produto_cliente " +
						"join cliente on (cliente = cliente.id) " + 
						"join produto on (produto = produto.id)")
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<InteresseProdutoCliente> rowMapper() {		
		return new RowMapper<InteresseProdutoCliente>() {

			@Override
			public InteresseProdutoCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				
				Produto produto = new Produto();
				// Utilizando o indice para obter os valores, já que existe mais de uma propriedade com o nome Id e Nome
				// Pode ser resolvido também utilizando Alias nas consultas
				produto.setId(rs.getInt(6));
				produto.setNome(rs.getString(7));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				
				InteresseProdutoCliente interesseProdutoCliente = new InteresseProdutoCliente();
				interesseProdutoCliente.setCliente(cliente);
				interesseProdutoCliente.setProduto(produto);
				return interesseProdutoCliente;
			}
			
		};
	}

}
