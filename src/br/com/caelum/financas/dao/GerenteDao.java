package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Gerente;
import br.com.caelum.financas.modelo.GerenteConta;

@Stateless
public class GerenteDao {

	@Inject
	private EntityManager manager;

	public void adiciona(Gerente gerente) {
		this.manager.joinTransaction();
		manager.persist(gerente);
	}

	public void remover(Gerente gerente) {
		this.manager.joinTransaction();
		Gerente gerenteDelete = busca(gerente.getId());
		manager.remove(gerenteDelete);
	}

	public void altera(Gerente gerente) {
		this.manager.joinTransaction();
		manager.merge(gerente);
	}

	public GerenteConta busca(Integer id) {
		return manager.find(GerenteConta.class, id);
	}

	public List<Gerente> lista() {
		return manager.createQuery("FROM Gerente", Gerente.class).getResultList();
	}

}
