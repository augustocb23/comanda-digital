package main.persistence.dao;

import main.domain.Comanda;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class ComandaDaoImpl extends AbstractDao<Comanda, Long> implements ComandaDao {
	@Override
	public Map<Long, String> findByTable(Integer table) {
		Map<Long, String> result = new HashMap<>();
		createQuery("FROM Comanda WHERE mesa = ?1", table).forEach((c) -> result.put(c.getCodigo(), c.getNome()));
		return result;
	}

	public Set<Integer> findTables() {
		Set<Integer> result = new TreeSet<>();
		createQuery("FROM Comanda WHERE status = 'A'").forEach((c) -> result.add(c.getMesa()));
		return result;
	}
}
