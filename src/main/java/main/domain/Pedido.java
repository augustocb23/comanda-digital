package main.domain;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "pedido")
public class Pedido extends AbstractEntity<Integer> {
	@ManyToOne
	@JoinColumn(name = "produto")
	private Produto produto;
	@Column(nullable = false)
	private Integer quantidade = 1;
	@Column(nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Status status = Status.S;
}
