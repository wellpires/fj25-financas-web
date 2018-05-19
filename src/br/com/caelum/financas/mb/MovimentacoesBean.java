package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.CategoriaDao;
import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.dao.MovimentacaoDao;
import br.com.caelum.financas.modelo.Categoria;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

@Named
@ViewScoped
public class MovimentacoesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MovimentacaoDao movimentacaoDao;
	
	@Inject
	private ContaDao contaDao;
	
	@Inject
	private CategoriaDao categoriaDao;
	
	private List<Movimentacao> movimentacoes;
	private Movimentacao movimentacao = new Movimentacao();
	private Integer contaId;
	private Integer categoriaId;
	
	private List<Categoria> categorias;
	
	
	public void grava() {
		System.out.println("Fazendo a gravacao da movimentacao");
		Conta conta = contaDao.busca(contaId);
		movimentacao.setConta(conta);
		
		movimentacaoDao.adiciona(movimentacao);
		this.movimentacoes = movimentacaoDao.listaComCategorias();
		limpaFormularioDoJSF();
	}
	

	public void remove() {
		System.out.println("Removendo a movimentacao");
		movimentacaoDao.remove(movimentacao);
		this.movimentacoes = movimentacaoDao.listaComCategorias();
		limpaFormularioDoJSF();
	}
	
	public void adicionaCategoria(){
		if(Objects.nonNull(categoriaId) && categoriaId > BigDecimal.ZERO.intValue()){
			Categoria categoria = categoriaDao.busca(categoriaId);
			movimentacao.getCategorias().add(categoria);
		}
	}

	public List<Movimentacao> getMovimentacoes() {
		if(Objects.isNull(movimentacoes)){
			this.movimentacoes = movimentacaoDao.lista();
		}
		return movimentacoes;
	}
	
	public Movimentacao getMovimentacao() {
		if(movimentacao.getData()==null) {
			movimentacao.setData(LocalDateTime.now());
		}
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}
	

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public List<Categoria> getCategorias() {
		if(Objects.isNull(categorias)){
			System.out.println("Listando as categorias");
			categorias = categoriaDao.lista();
		}
		return categorias;
	}


	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}


	/**
	 * Esse metodo apenas limpa o formulario da forma com que o JSF espera.
	 * Invoque-o no momento manager que precisar do formulario vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.movimentacao = new Movimentacao();
	}

	public TipoMovimentacao[] getTiposDeMovimentacao() {
		return TipoMovimentacao.values();
	}
}
