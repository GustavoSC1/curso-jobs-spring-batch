package com.gustavo.primeiroprojetospringbatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PropsConfig {
	
	@Bean
	// Útil em cenários onde é necessário trabalhar com propriedades externas ou arquivos personalizados de configuração
	public PropertySourcesPlaceholderConfigurer config() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new FileSystemResource("/Users/Gustavo/Estudos/SpringBatch/primeirojobspringbatch/application.properties"));
		return configurer;
	}

}
