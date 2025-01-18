package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

// A interface ItemStreamReader<Cliente>, que é usada para leitura de itens com suporte a  persistência e gerenciamento de estado em um 
// contexto de execução. Ela é projetada para cenários em que você precisa acompanhar o progresso do processamento de itens.

// A interface ResourceAwareItemReaderItemStream cpermite que o leitor seja configurado dinamicamente com um recurso específico 
// antes de começar a leitura. Essa interface é útil em cenários onde você precisa processar vários recursos, como arquivos ou 
// fluxos de entrada, dentro de um único job.
public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente>, ResourceAwareItemReaderItemStream<Cliente> {
	
	// Armazena temporariamente o objeto atual lido do delegate
	private Object objAtual;
	
	// É um outro ItemStreamReader (nesse caso o FlatFileItemReader), configurado para ler linhas do arquivo
	private FlatFileItemReader<Object> delegate;
	
	public ArquivoClienteTransacaoReader(FlatFileItemReader<Object> delegate) {
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
	
	@Override
	// Permite associar um recurso específico ao leitor antes de começar a leitura.
	public void setResource(Resource resource) {
		delegate.setResource(resource);	
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
