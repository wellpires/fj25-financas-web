package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.exception.ValorInvalidoException;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.ValorPorMesEAno;

@Stateless
public class MovimentacaoDao {

//	@PersistenceContext
	@Inject
	private EntityManager manager;

	public void adiciona(Movimentacao movimentacao) {
		this.manager.joinTransaction();
		this.manager.persist(movimentacao);
		
		if(movimentacao.getValor().compareTo(BigDecimal.ZERO) < 0){
			throw new ValorInvalidoException("Movimentação negativa");
		}
		
	}
	
	public Movimentacao busca(Integer id) {
		return this.manager.find(Movimentacao.class, id);
	}

	public List<Movimentacao> lista() {
		return this.manager.createQuery("select m from Movimentacao m", Movimentacao.class).getResultList();
	}

	public void remove(Movimentacao movimentacao) {
		this.manager.joinTransaction();
		Movimentacao movimentacaoParaRemover = this.manager.find(Movimentacao.class, movimentacao.getId());
		this.manager.remove(movimentacaoParaRemover);
	}

	public List<Movimentacao> listaTodasMovimentacoes(Conta conta) {
	
		StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT				");
		strQuery.append(" 	m					");
		strQuery.append(" FROM					");
		strQuery.append(" 	Movimentacao m 		");
		strQuery.append(" WHERE 				");
		strQuery.append(" 	m.conta = :conta	");
		strQuery.append(" ORDER BY				");
		strQuery.append(" 	m.valor DESC		");
		
		return manager
					.createQuery(strQuery.toString(), Movimentacao.class)
					.setParameter("conta", conta)
					.getResultList();
	}
	
	public List<Movimentacao> listarPorValorETipo(BigDecimal valor, TipoMovimentacao tipoMovimentacao) {
		
		StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT						");
		strQuery.append(" 	m							");
		strQuery.append(" FROM							");
		strQuery.append(" 	Movimentacao m 				");
		strQuery.append(" WHERE 						");
		strQuery.append(" 	m.valor <= :valor			");
		strQuery.append(" AND							");
		strQuery.append(" 	m.tipoMovimentacao = :tipo	");
		
		TypedQuery<Movimentacao> query = manager.createQuery(strQuery.toString(), Movimentacao.class);
		query.setParameter("valor", valor);
		query.setParameter("tipo", tipoMovimentacao);
		query.setHint("org.hibernate.cacheable", "true");
		
		return query.getResultList();
	}

	public BigDecimal calculaTotalMovimentado(Conta conta, TipoMovimentacao tipoMovimentacao) {
		
		StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT						");
		strQuery.append(" 	SUM(m.valor)				");
		strQuery.append(" FROM							");
		strQuery.append(" 	Movimentacao m 				");
		strQuery.append(" WHERE 						");
		strQuery.append(" 	m.conta <= :conta			");
		strQuery.append(" AND							");
		strQuery.append(" 	m.tipoMovimentacao = :tipo	");
		
		return manager
				.createQuery(strQuery.toString(), BigDecimal.class)
				.setParameter("conta", conta)
				.setParameter("tipo", tipoMovimentacao)
				.getSingleResult();
	}

	public List<Movimentacao> buscaTodasAsMovimentacoesDaConta(String titular) {
		
		StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT							");
		strQuery.append(" 	m								");
		strQuery.append(" FROM								");
		strQuery.append(" 	Movimentacao m 					");
		strQuery.append(" WHERE 							");
		strQuery.append(" 	m.conta.titular LIKE :titular	");
		
		return manager
				.createQuery(strQuery.toString(), Movimentacao.class)
				.setParameter("titular", "%".concat(titular).concat("%"))
				.getResultList();
	}

	public List<ValorPorMesEAno> listaMesesComMovimentacoes(Conta conta, TipoMovimentacao tipoMovimentacao) {
		
		StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT																							");
		strQuery.append(" 	new br.com.caelum.financas.modelo.ValorPorMesEAno(year(m.data), month(m.data), SUM(m.valor))	");
		strQuery.append(" FROM																								");
		strQuery.append(" 	Movimentacao m 																					");
		strQuery.append(" WHERE 																							");
		strQuery.append(" 	m.conta = :conta AND																			");
		strQuery.append(" 	m.tipoMovimentacao = :tipo																		");
		strQuery.append(" GROUP BY																							");
		strQuery.append(" 	year(m.data), month(m.data)																		");
		strQuery.append(" ORDER BY																							");
		strQuery.append(" 	SUM(m.valor) DESC																				");
		
		return manager.createQuery(strQuery.toString(), ValorPorMesEAno.class)
				.setParameter("conta", conta)
				.setParameter("tipo", tipoMovimentacao)
				.getResultList();
	}

	public List<Movimentacao> listaComCategorias() {

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT				");
		strQuery.append("	DISTINCT m		");
		strQuery.append("FROM				");
		strQuery.append("	Movimentacao m	");
		strQuery.append("LEFT JOIN FETCH	");
		strQuery.append("	m.categorias	");
		
		return manager.createQuery(strQuery.toString(), Movimentacao.class)
				.getResultList();
	}

}
