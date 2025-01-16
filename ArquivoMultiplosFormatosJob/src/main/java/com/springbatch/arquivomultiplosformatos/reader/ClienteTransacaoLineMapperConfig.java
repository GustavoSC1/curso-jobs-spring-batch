package com.springbatch.arquivomultiplosformatos.reader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

@Configuration
public class ClienteTransacaoLineMapperConfig {
	
	@Bean
	public PatternMatchingCompositeLineMapper lineMapper() {
		// Permite lidar com entradas heterogêneas em um único arquivo e mapear cada linha 
		// para um tipo de objeto específico com base em padrões (expressões regulares).
		PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper<>();
		// Responsável por dividir a linha em partes (ou tokens) com base em algum delimitador ou formato.
		lineMapper.setTokenizers(tokenizers());
		// Converte os tokens em um objeto do modelo de domínio.
		lineMapper.setFieldSetMappers(fieldSetMappers());
		
		return lineMapper;
	}
	
	private Map<String, FieldSetMapper> fieldSetMappers() {
		Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();
		fieldSetMappers.put("0*", fieldSetMapper(Cliente.class));
		fieldSetMappers.put("1*", fieldSetMapper(Transacao.class));
		
		return fieldSetMappers;
	}
	
	private FieldSetMapper fieldSetMapper(Class classe) {
		// Usa os nomes das propriedades do objeto e os campos do FieldSet para realizar o mapeamento.
		BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(classe);
		return fieldSetMapper;
	}
	
	private Map<String, LineTokenizer> tokenizers() {
		Map<String, LineTokenizer> tokenizers = new HashMap<>();
		tokenizers.put("0*", clienteLineTokenizer());
		tokenizers.put("1*", transacaoLineTokenizer());
		
		return tokenizers;
	}
	
	private LineTokenizer clienteLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("nome", "sobrenome", "idade", "email");	
		lineTokenizer.setIncludedFields(1, 2, 3, 4);
		
		return lineTokenizer;
	}
	
	private LineTokenizer transacaoLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("id", "descricao", "valor");	
		lineTokenizer.setIncludedFields(1, 2, 3);
		
		return lineTokenizer;
	}

}
