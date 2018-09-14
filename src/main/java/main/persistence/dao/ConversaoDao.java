package main.persistence.dao;

import main.domain.Conversao;

import java.util.List;

public interface ConversaoDao {
	void save(Conversao conversao);

	void update(Conversao conversao);

	void delete(Long id);

	Conversao findById(Long id);

	List<Conversao> findAll();
}
