package main.domain;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<Integer> {
	@Column(nullable = false, unique = true)
	private String login;
	@Column(nullable = false, length = 60, columnDefinition = "CHAR(60)")
	private String senha;
	@Column(nullable = false)
	private String nome;
	@ManyToOne
	@JoinColumn(name = "grupo", nullable = false)
	private Grupo grupo;
	@Column(nullable = false, columnDefinition = "BIT(1) DEFAULT TRUE")
	private Boolean ativo = true;

	@Override
	public String toString() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public List<Autorizacao> getAutorizacoes() {
		return grupo.getAutorizacoes();
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}
}
