package main.persistence.dao;

import main.domain.Pedido;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PedidoDaoImpl extends AbstractDao<Pedido, Long> implements PedidoDao {
	@Override
	public List<Object[]> consumptionReport(LocalDate inicio, LocalDate fim) {
		return getEntityManager().createQuery("SELECT r, SUM(p.quantidade) AS quant FROM Pedido p JOIN" +
						" Produto r ON p.produto = r.codigo WHERE p.comanda.data BETWEEN '" + inicio.toString() +
						"' AND '" + fim.toString() + "' GROUP BY p.produto",
				Object[].class).getResultList();
	}
}
