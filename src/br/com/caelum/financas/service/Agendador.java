package br.com.caelum.financas.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;

//@Stateless
@AccessTimeout(unit = TimeUnit.SECONDS, value = 10)
@Singleton
public class Agendador {

	private static int totalCriado;

	public void executa() {
		System.out.printf("%d instancias criadas %n", totalCriado);

		// simulando demora de 4s na execucao
		try {
			System.out.printf("Executando %s %n", this);
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
	}
	
	@PostConstruct
	public void posContrucao(){
		System.out.println("Criando agendador");
		totalCriado++;
	}
	
	@PreDestroy
	public void preDestruicao(){
		System.out.println("Destruindo agendador");
	}

}
