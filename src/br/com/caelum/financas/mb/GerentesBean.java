package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.GerenteDao;
import br.com.caelum.financas.modelo.Gerente;
import br.com.caelum.financas.modelo.GerenteConta;

@Named
@ViewScoped
public class GerentesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Gerente gerente = new GerenteConta();
	private List<Gerente> gerentes;

	@Inject
	private GerenteDao gerenteDao;

	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}

	public List<Gerente> getGerentes() {

		if (Objects.isNull(gerentes)) {
			this.gerentes = gerenteDao.lista();
		}

		return gerentes;
	}

	public void grava() {
		gerenteDao.adiciona(gerente);
		getGerentes();
		limpaFormularioDoJSF();
	}

	public void remove() {
		gerenteDao.remover(gerente);
		getGerentes();
		limpaFormularioDoJSF();
	}

	private void limpaFormularioDoJSF() {
		this.setGerente(new GerenteConta());
	}

}
