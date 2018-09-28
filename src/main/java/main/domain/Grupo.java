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
	@JoinTable(name = "grupo_permissao", joinColumns = {@JoinColumn(name = "grupo")},
			inverseJoinColumns = {@JoinColumn(name = "permissao")})
	private List<Permissao> permissoes;

	@Override
	public String toString() {
		return getNome();
	}

	List<Permissao> getPermissoes() {
		return permissoes;
	}

	public String getNome() {
		return nome;
	}
}
