package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {
	
	// Armazena temporariamente o objeto atual lido do delegate
	private Object objAtual;
	
	// É um outro ItemStreamReader (nesse caso o FlatFileItemReader), configurado para ler linhas do arquivo
	private ItemStreamReader<Object> delegate;
	
	public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);
	}
	
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
	}
	
	@Override
	public void close() throws ItemStreamException {
		delegate.close();
	}
	
	// Agrega as transações associadas a um cliente em um único objeto Cliente.
	@Override
	public Cliente read() throws Exception {
		if(objAtual == null)
			objAtual = delegate.read();
		
		Cliente cliente = (Cliente) objAtual;
		objAtual = null;
		
		if(cliente != null) {
			while (peek() instanceof Transacao)
				cliente.getTransacoes().add((Transacao) objAtual);
		}
		
		return cliente;
	}
	
	private Object peek() throws Exception {
		objAtual = delegate.read();
		return objAtual;
	}

}
