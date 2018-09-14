package main.domain;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "conversao")
public class Conversao extends AbstractEntity<Integer> {
	@ManyToOne
	@JoinColumn(name = "produto", nullable = false)
	private Produto produto;
	@ManyToOne
	@JoinColumn(name = "ingrediente", nullable = false)
	private Ingrediente ingrediente;
	@Column(name = "unidade", nullable = false, columnDefinition = "CHAR(2)", length = 2)
	private String unidade;
	@Column(name = "fator", nullable = false)
	private Double fator;
}
