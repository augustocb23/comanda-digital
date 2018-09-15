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
	@ManyToMany
	@JoinTable(name = "funcionario_autorizacao",
			joinColumns = {@JoinColumn(name = "funcionario")},
			inverseJoinColumns = {@JoinColumn(name = "autorizacao")})
	private List<Autorizacao> autorizacoes;
	@Column(nullable = false)
	private Boolean ativo;

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
		return autorizacoes;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}
}
