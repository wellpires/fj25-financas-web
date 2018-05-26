package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.dao.GerenteDao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.GerenteConta;

@Named
@ViewScoped
public class ContasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContaDao contaDao;

	@Inject
	private GerenteDao gerenteDao;

	private Conta conta = new Conta();
	private List<Conta> contas;
	private Integer gerenteId;

	public void grava() {
		System.out.println("Gravando a conta");

		// if (Objects.nonNull(gerenteId)) {
		// Gerente gerenteRelacionado = gerenteDao.busca(gerenteId);
		// this.conta.setGerente(gerenteRelacionado);
		// }

		if (Objects.nonNull(gerenteId)) {
			GerenteConta gerenteRelacionado = gerenteDao.busca(gerenteId);
			gerenteRelacionado.setNumeroDaConta(this.conta.getNumero());
			this.conta.setGerente(gerenteRelacionado);
		}

		if (Objects.nonNull(this.conta.getId())) {
			contaDao.altera(conta);
		} else {
			contaDao.adiciona(conta);
		}
		this.contas = contaDao.lista();

		limpaFormularioDoJSF();
	}

	public List<Conta> getContas() {
		System.out.println("Listando as contas");
		if (Objects.isNull(contas)) {
			this.contas = contaDao.lista().stream().filter(Objects::nonNull).collect(Collectors.toList());
		}
		return this.contas;
	}

	public void remove() {
		System.out.println("Removendo a conta");

		contaDao.remove(conta);
		this.contas = contaDao.lista();

		limpaFormularioDoJSF();
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Integer getGerenteId() {
		return gerenteId;
	}

	public void setGerenteId(Integer gerenteId) {
		this.gerenteId = gerenteId;
	}

	/**
	 * Esse metodo apenas limpa o formulario da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulario vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.conta = new Conta();
	}
}
