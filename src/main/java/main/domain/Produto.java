package main.domain;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "produto")
public class Produto extends AbstractEntity<Integer> {
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "estoque")
	private Integer estoque;
	@ManyToMany
	@JoinTable(name = "produto_ingrediente",
			joinColumns = {@JoinColumn(name = "produto")},
			inverseJoinColumns = {@JoinColumn(name = "ingrediente")})
	private List<Ingrediente> ingredientes;
	@Column(name = "imagem")
	private String imagem;

	@Override
	public String toString() {
		return nome;
	}
}
