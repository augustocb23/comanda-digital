package main.domain;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "grupo")
public class Grupo extends AbstractEntity<Long> {
	@Column(nullable = false, unique = true)
	private String nome;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "grupo_permissao", joinColumns = {@JoinColumn(name = "grupo")},
			inverseJoinColumns = {@JoinColumn(name = "permissao")})
	private Set<Permissao> permissoes;
	@Transient
	private String permissoesString;

	@Override
	public String toString() {
		return getNome();
	}

	public String getNome() {
		return nome;
	}

	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public String getPermissoesString() {
		return permissoesString;
	}

	public void setPermissoesString(String permissoesString) {
		this.permissoesString = permissoesString;
	}

	public boolean isEditavel() {
		return !(nome.contentEquals("administrador") || nome.contentEquals("atendente"));
	}

	public void setNome(String nome) {
		this.nome = nome.toLowerCase();
	}
}
