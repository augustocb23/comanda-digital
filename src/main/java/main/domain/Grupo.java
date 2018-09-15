package main.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "grupo")
public class Grupo extends AbstractEntity<Integer> {
	@Column(nullable = false, unique = true)
	private String nome;
	@OneToMany
	@Column(nullable = false)
	private List<Autorizacao> autorizacoes;

	@Override
	public String toString() {
		return nome;
	}

	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}
}
