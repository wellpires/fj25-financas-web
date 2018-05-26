package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;

@Stateless
public class ContaDao {

	@Inject
	private EntityManager manager;

	public void adiciona(Conta conta) {
		this.manager.joinTransaction();
		this.manager.persist(conta);
	}

	public void altera(Conta conta){
		this.manager.joinTransaction();
		this.manager.merge(conta);
	}
	
	public Conta busca(Integer id) {
		return this.manager.find(Conta.class, id);
	}

	public List<Conta> lista() {
		return this.manager.createQuery("FROM Conta c", Conta.class)
				.getResultList();
	}

	public void remove(Conta conta) {
		this.manager.joinTransaction();
		Conta contaParaRemover = this.manager.find(Conta.class, conta.getId());
		this.manager.remove(contaParaRemover);
	}

	public int trocaNomeDoBancoEmLote(String antigoNomeBanco, String novoNomeBanco){
		
		StringBuilder strQuery = new StringBuilder();
		strQuery.append(" UPDATE					");
		strQuery.append(" 	Conta c					");
		strQuery.append(" SET						");
		strQuery.append(" 	c.banco = :novoNome		");
		strQuery.append(" WHERE						");
		strQuery.append(" 	c.banco = :antigoNome	");
		
		return this.manager.createQuery(strQuery.toString())
					.setParameter("novoNome", novoNomeBanco)
					.setParameter("antigoNome", antigoNomeBanco)
					.executeUpdate();
		
	}
	
}




