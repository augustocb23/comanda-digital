package main.domain;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "grupo")
public class Grupo extends AbstractEntity<Integer> {
	@Column(nullable = false, unique = true)
	private String nome;
	@ManyToMany
	@JoinTable(name = "grupo_autorizacao", joinColumns = {@JoinColumn(name = "grupo")},
			inverseJoinColumns = {@JoinColumn(name = "autorizacao")})
	private List<Autorizacao> autorizacoes;

	@Override
	public String toString() {
		return nome;
	}

	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}
}
