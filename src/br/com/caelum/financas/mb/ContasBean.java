package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.modelo.Conta;

@Named
@ViewScoped
public class ContasBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Inject
    private ContaDao contaDao;
    
	private Conta conta = new Conta();
	private List<Conta> contas;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void grava() {
		System.out.println("Gravando a conta");

		if(Objects.nonNull(this.conta.getId())){
			contaDao.altera(conta);
		} else {
			contaDao.adiciona(conta);
		}
		this.contas = contaDao.lista();
		
		limpaFormularioDoJSF();
	}

	public List<Conta> getContas() {
		System.out.println("Listando as contas");
		if(Objects.isNull(contas)){
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

	/**
	 * Esse metodo apenas limpa o formulario da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulario vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.conta = new Conta();
	}
}
