package main.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<Integer> {
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "tipo", nullable = false)
	private Character tipo;
	@Column(name = "ativo", nullable = false)
	private Boolean ativo;

	@Override
	public String toString() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
