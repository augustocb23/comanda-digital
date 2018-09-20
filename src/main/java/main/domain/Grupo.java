package main.domain;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "grupo")
public class Grupo extends AbstractEntity<Long> {
	@Column(nullable = false, unique = true)
	private String nome;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "grupo_autorizacao", joinColumns = {@JoinColumn(name = "grupo")},
			inverseJoinColumns = {@JoinColumn(name = "autorizacao")})
	private List<Autorizacao> autorizacoes;

	@Override
	public String toString() {
		return getNome();
	}

	List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public String getNome() {
		return nome;
	}
}
