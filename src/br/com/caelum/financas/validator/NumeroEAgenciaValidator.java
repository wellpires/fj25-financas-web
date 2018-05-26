package br.com.caelum.financas.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.caelum.financas.modelo.Conta;

public class NumeroEAgenciaValidator implements ConstraintValidator<NumeroEAgencia, Conta> {

	@Override
	public void initialize(NumeroEAgencia numero) {
	}

	@Override
	public boolean isValid(Conta conta, ConstraintValidatorContext context) {
		if(Objects.isNull(conta)){
			return true;
		}
		
		boolean isEmptyAgencia = (Objects.isNull(conta.getAgencia()) || conta.getAgencia().trim().isEmpty());
		boolean isEmptyNumero = (Objects.isNull(conta.getNumero()) || conta.getNumero().trim().isEmpty());
		
		return !(isEmptyAgencia ^isEmptyNumero);
	}

}
