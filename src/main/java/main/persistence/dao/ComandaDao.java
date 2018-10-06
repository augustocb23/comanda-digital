package main.persistence.dao;

import main.domain.Comanda;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ComandaDao {
	void save(Comanda comanda);

	void update(Comanda comanda);

	void delete(Long id);

	Comanda findById(Long id);

	List<Comanda> findAll();

	Set<Integer> findTables();

	Map<Long, String> findByTable(Integer table);
}
