package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.financas.modelo.Conta;

@Stateless
public class ContaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Conta conta) {
		this.manager.persist(conta);
	}

	public void altera(Conta conta){
		Conta contaManaged = busca(conta.getId());
		contaManaged.setAgencia(conta.getAgencia());
		contaManaged.setBanco(conta.getBanco());
		contaManaged.setNumero(conta.getNumero());
		contaManaged.setTitular(conta.getTitular());
		
		this.manager.merge(contaManaged);
	}
	
	public Conta busca(Integer id) {
		return this.manager.find(Conta.class, id);
	}

	public List<Conta> lista() {
		return this.manager.createQuery("FROM Conta c", Conta.class)
				.getResultList();
	}

	public void remove(Conta conta) {
		Conta contaParaRemover = this.manager.find(Conta.class, conta.getId());
		this.manager.remove(contaParaRemover);
	}

}




