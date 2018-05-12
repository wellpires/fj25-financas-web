package br.com.caelum.financas.modelo;

import java.math.BigDecimal;

public class ValorPorMesEAno {

	private Integer ano;
	private Integer mes;
	private BigDecimal valor;

	public ValorPorMesEAno(Integer ano, Integer mes, BigDecimal valor) {
		this.ano = ano;
		this.mes = mes;
		this.valor = valor;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
