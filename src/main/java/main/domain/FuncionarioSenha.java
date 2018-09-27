package main.domain;

import javax.validation.constraints.NotNull;

public class FuncionarioSenha extends Funcionario {
	@NotNull(message = "Insira a senha atual")
	private String senhaAtual;
	@NotNull(message = "Insira a nova senha")
	private String senhaNova;
	@NotNull(message = "Redigite a nova senha")
	private String senhaConfirma;

	public void setRawSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaConfirma() {
		return senhaConfirma;
	}

	public void setSenhaConfirma(String senhaConfirma) {
		this.senhaConfirma = senhaConfirma;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}
}
