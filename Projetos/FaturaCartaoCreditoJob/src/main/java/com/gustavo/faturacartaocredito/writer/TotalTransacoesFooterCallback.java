package com.gustavo.faturacartaocredito.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import com.gustavo.faturacartaocredito.dominio.FaturaCartaoCredito;


public class TotalTransacoesFooterCallback implements FlatFileFooterCallback {
	
	private Double total = 0.0;
	
	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.write(String.format("\n%121s", "Total: " + NumberFormat.getCurrencyInstance().format(total)));
	}
	
	@BeforeWrite
	public void beforeWrite(Chunk<FaturaCartaoCredito> faturas) {
		for(FaturaCartaoCredito faturaCartaoCredito: faturas)
			total += faturaCartaoCredito.getTotal();
	}
	
	@AfterChunk
	public void afterChunck(ChunkContext chunkContext) {
		total = 0.0;
	}

}
