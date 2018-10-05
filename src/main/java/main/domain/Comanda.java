package main.domain;

import main.domain.enumerator.StatusComanda;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "comanda")
public class Comanda extends AbstractEntity<Integer> {
	@Column(nullable = false)
	private Integer mesa;
	private String nomes;
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
}
