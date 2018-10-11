package main.persistence.dao;

import main.domain.Comanda;
import main.domain.enumerator.StatusComanda;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComandaDaoImpl extends AbstractDao<Comanda, Long> implements ComandaDao {
	public List<Comanda> findByMesa(Integer mesa) {
		return createQuery("FROM Comanda WHERE mesa = ?1", mesa);
	}

	@Override
	public void setMesa(Comanda comanda) {
		Comanda upd = findById(comanda.getCodigo());
		upd.setMesa(comanda.getMesa());
	}

	@Override
	public void setNome(Comanda comanda) {
		Comanda upd = findById(comanda.getCodigo());
		upd.setNome(comanda.getNome());
	}

	public List<Comanda> findByStatus(StatusComanda status) {
		return createQuery("FROM Comanda WHERE status = ?1", status);
	}
}
