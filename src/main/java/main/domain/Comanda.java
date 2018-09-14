package main.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "comanda")
public class Comanda extends AbstractEntity<Integer> {
	@Column(name = "mesa", nullable = false)
	private Integer mesa;
	@Column(name = "nomes")
	private String nomes;
	@Column(name = "data", columnDefinition = "DATETIME", nullable = false)
	private Date data;
	@ManyToOne
	@JoinColumn(name = "atendente")
	private Funcionario atendente;
	@OneToMany(mappedBy = "codigo")
	private List<Pedido> pedidos;
}
