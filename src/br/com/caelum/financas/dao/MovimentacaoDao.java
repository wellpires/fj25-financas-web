package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.financas.exception.ValorInvalidoException;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

@Stateless
public class MovimentacaoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Movimentacao movimentacao) {
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
		
		return manager
					.createQuery(strQuery.toString(), Movimentacao.class)
					.setParameter("valor", valor)
					.setParameter("tipo", tipoMovimentacao)
					.getResultList();
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

}
