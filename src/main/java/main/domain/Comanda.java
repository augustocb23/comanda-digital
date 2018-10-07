package main.domain;

import main.domain.enumerator.StatusComanda;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "comanda")
public class Comanda extends AbstractEntity<Long> {
	@Column(nullable = false)
	private Integer mesa;
	private String nome;
	@Column(columnDefinition = "DATETIME", nullable = false)
	private Date data;
	@ManyToOne
	@JoinColumn(name = "atendente")
	private Funcionario atendente;
	@OneToMany(mappedBy = "comanda")
	private List<Pedido> pedidos;
	@Column(nullable = false, columnDefinition = "CHAR(1)")
	@Enumerated(EnumType.STRING)
	private StatusComanda status = StatusComanda.A;

	public int senhaComanda() {
		return Objects.hash(data);
	}

	public void removeAtendente() {
		atendente = null;
	}

	public Funcionario getAtendente() {
		return atendente;
	}

	public void setAtendente(Funcionario atendente) {
		this.atendente = atendente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getMesa() {
		return mesa;
	}

	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public StatusComanda getStatus() {
		return status;
	}

	public void setStatus(StatusComanda status) {
		this.status = status;
	}
}
