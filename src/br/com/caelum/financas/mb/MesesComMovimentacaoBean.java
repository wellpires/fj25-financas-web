package br.com.caelum.financas.mb;

import br.com.caelum.financas.dao.MovimentacaoDao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.ValorPorMesEAno;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class MesesComMovimentacaoBean {

	private Conta conta = new Conta();
	private List<ValorPorMesEAno> valoresPorMesEAno;
	private TipoMovimentacao tipoMovimentacao;

	@Inject
	private MovimentacaoDao movimentacaoDao;

	public void lista() {
		valoresPorMesEAno = movimentacaoDao.listaMesesComMovimentacoes(conta, tipoMovimentacao);
		System.out.println("Listando as contas pelos valores movimentados no mes");
	}

	public MovimentacaoDao getMovimentacaoDao() {
		return movimentacaoDao;
	}

	public void setMovimentacaoDao(MovimentacaoDao movimentacaoDao) {
		this.movimentacaoDao = movimentacaoDao;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Conta getConta() {
		return conta;
	}

	public List<ValorPorMesEAno> getValoresPorMesEAno() {
		return valoresPorMesEAno;
	}

	public void setValoresPorMesEAno(List<ValorPorMesEAno> valoresPorMesEAno) {
		this.valoresPorMesEAno = valoresPorMesEAno;
	}

}
