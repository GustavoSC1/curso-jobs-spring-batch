package com.gustavo.faturacartaocredito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FaturaCartaoCreditoJobApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(FaturaCartaoCreditoJobApplication.class, args);
		context.close();
	}

}
