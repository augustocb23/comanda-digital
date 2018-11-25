package main.persistence.dao;

import main.domain.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface PedidoDao {
	void save(Pedido pedido);

	void update(Pedido pedido);

	void delete(Long id);

	Pedido findById(Long id);

	List<Pedido> findAll();

	List<Object[]> consumptionReport(LocalDate inicio, LocalDate fim);
}
