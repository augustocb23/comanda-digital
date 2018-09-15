package main.domain;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "pedido")
public class Pedido extends AbstractEntity<Integer> {
	@ManyToOne
	@JoinColumn(name = "comanda", nullable = false)
	private Comanda comanda;
	@ManyToOne
	@JoinColumn(name = "produto", nullable = false)
	private Produto produto;
	@Column(nullable = false)
	private Integer quantidade = 1;
	@Column(nullable = false, columnDefinition = "CHAR(1)")
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.S;
}
