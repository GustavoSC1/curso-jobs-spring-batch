package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;

@Component
public class DemonstrativoOrcamentarioRodape implements FlatFileFooterCallback {
	
	private Double totalGeral = 0.0;
	
	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.append("\n");
		writer.append(String.format("\t\t\t\t\t\t\t  Total: %s\n", NumberFormat.getCurrencyInstance().format(totalGeral)));
		writer.append(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s\n", "fkyew6868fewjfhjjewf"));
	}
	
	// Usada para executar um método antes que o ItemWriter grave os itens no destino final.
	@BeforeWrite
	public void beforeWrite(Chunk<GrupoLancamento> grupos) {
		for(GrupoLancamento grupoLancamento: grupos) {
			totalGeral += grupoLancamento.getTotal();
		}
	}
	
	// Utilizado para executar ações após a conclusão de um Chunk
	@AfterChunk
	public void afterChunkChunkCOntext (ChunkContext context) {
		totalGeral = 0.0;
	}

}
