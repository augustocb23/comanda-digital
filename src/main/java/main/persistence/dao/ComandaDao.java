package main.persistence.dao;

import main.domain.Comanda;
import main.domain.enumerator.StatusComanda;

import java.util.List;

public interface ComandaDao {
	void save(Comanda comanda);

	void update(Comanda comanda);

	void delete(Long id);

	List<Comanda> findAbertasByMesa(Integer mesa);

	void setMesa(Comanda comanda);

	void setNome(Comanda comanda);

	Comanda findById(Long id);

	List<Comanda> findAll();

	List<Comanda> findByStatus(StatusComanda status);

	void setStatus(Comanda comanda);
}
