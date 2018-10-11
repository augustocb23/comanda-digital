package main.persistence.dao;

import main.domain.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoDaoImpl extends AbstractDao<Pedido, Long> implements PedidoDao {
	@Override
	public void setStatus(Pedido pedido) {
		Pedido upd = findById(pedido.getCodigo());
		upd.setStatus(pedido.getStatus());
	}
}
