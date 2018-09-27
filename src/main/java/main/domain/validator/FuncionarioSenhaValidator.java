package main.domain.validator;

import main.domain.FuncionarioSenha;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FuncionarioSenhaValidator implements Validator {
	@Override
	public boolean supports(Class<?> objClass) {
		return FuncionarioSenha.class.equals(objClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		//converte para FuncionarioSenha
		FuncionarioSenha func = (FuncionarioSenha) obj;

		//senha atual está correta
		if (!func.validaSenha(func.getSenhaAtual()))
			errors.rejectValue("senhaAtual", "senhaInvalida", "Senha inválida");

		//senha foi confirmada
		ValidationUtils.rejectIfEmpty(errors, "senhaConfirma", "senhaEmBranco",
				"Redigite a nova senha");

		//senhas coincidem
		if (func.getSenhaNova() == null || !func.getSenhaNova().contentEquals(func.getSenhaConfirma()))
			errors.rejectValue("senhaNova", "senhaNaoCoincide", "Senhas não coincidem");

		//senha não contém apenas espaços
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "senhaNova", "senhaEspacos",
				"Senha não pode conter somente espaços");

		//salva a senha
		if (!errors.hasErrors())
			func.setSenha(func.getSenhaNova());
	}
}
