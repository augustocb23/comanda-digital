package main.domain;

import main.domain.enumerator.StatusPedido;

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

	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}
}
