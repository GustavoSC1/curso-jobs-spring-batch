package com.gustavo.primeiroprojetospringbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//Permite que o bean seja criado apenas quando o Step associado estiver em execução e que seja isolado para essa execução.
// Evitando a criação desnecessária de objetos durante a inicialização da aplicação.
// Isso é particularmente útil em cenários onde o bean depende de parâmetros dinâmicos que são passados no momento da execução do job.
@StepScope
public class ImprimeOlaTasklet implements Tasklet {
	
	@Value("#{jobParameters['nome']}") 
	private String nome;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println(String.format("Olá, %s!", nome));
		return RepeatStatus.FINISHED;
	}

}
