package com.springbatch.processadorvalidacao.processor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorvalidacao.dominio.Cliente;

@Configuration
public class ProcessadorValidacaoProcessorConfig {
	private Set<String> emails = new HashSet<>();
			
	@Bean
	public ItemProcessor<Cliente, Cliente> procesadorValidacaoProcessor() throws Exception {
		// Combina múltiplos processadores (ItemProcessor) em uma cadeia de processamento
		return new CompositeItemProcessorBuilder<Cliente, Cliente>()
				.delegates(beanValidatingProcessor(), emailValidatingProcessor())
				.build();		
	}
	
	private BeanValidatingItemProcessor<Cliente> beanValidatingProcessor() throws Exception {
		// Utiliza as anotações do Java Bean Validation para validar os objetos processados no batch
		// antes de o item ser passado para o próximo estágio do processamento
		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<>();
		processor.setFilter(true);
		// Verifica se o processador foi configurado corretamente antes de ser usado
		// Evita erros em tempo de execução ao lançar exceções em caso de configurações ausentes
		processor.afterPropertiesSet();
		return processor;
	}
	
	private ValidatingItemProcessor<Cliente> emailValidatingProcessor() {
		// Valida os itens processados usando um objeto Validator customizado
		ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<>();
		processor.setValidator(validator());
		processor.setFilter(true);
		return processor;
	}
	
	// Define regras de validação personalizadas para os itens processados
	private Validator<Cliente> validator() {
		return new Validator<Cliente>() {

			@Override
			public void validate(Cliente cliente) throws ValidationException {
				if(emails.contains(cliente.getEmail())) {
					throw new ValidationException(String.format("O cliente %s já foi processado!", cliente.getEmail()));
				}
				emails.add(cliente.getEmail());
			}	
			
		};
	}
	
}
