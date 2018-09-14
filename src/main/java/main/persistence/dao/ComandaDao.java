package main.persistence.dao;

import main.domain.Comanda;

import java.util.List;

public interface ComandaDao {
	void save(Comanda comanda);

	void update(Comanda comanda);

	void delete(Long id);

	Comanda findById(Long id);

	List<Comanda> findAll();
}
