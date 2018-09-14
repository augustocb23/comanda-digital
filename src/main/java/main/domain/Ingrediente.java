package main.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ingrediente")
public class Ingrediente extends AbstractEntity<Integer> {
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "estoque")
	private Integer estoque;
	@Column(name = "unidade", nullable = false, length = 2, columnDefinition = "CHAR(2)")
	private String unidade;

	@Override
	public String toString() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
