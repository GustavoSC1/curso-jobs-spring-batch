package com.springbatch.processadorscript.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ScriptItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorscript.dominio.Cliente;

@Configuration
public class ProcessadorScriptProcessorConfig {
	
	@Bean
	public ItemProcessor<Cliente, Cliente> processadorScriptProcessor() {
		// Spring Batch serve para processar itens usando um script escrito em uma linguagem de script, como Groovy, 
		// JavaScript, ou outras linguagens suportadas, durante o processamento de dados em um job.
		return new ScriptItemProcessorBuilder<Cliente, Cliente>()
				.language("neshorn")
				.scriptSource(
						"var email = item.getEmail();" +
						"var arquivoExiste = `ls | grep ${email}.txt`;" +
						"if (!arquivoExiste) item; else null;"
				 ).build();		
	}
	
}
