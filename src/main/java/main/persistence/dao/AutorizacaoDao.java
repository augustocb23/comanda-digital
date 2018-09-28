package main.persistence.dao;

import main.domain.Permissao;

import java.util.List;

public interface AutorizacaoDao {
	void save(Permissao permissao);

	void update(Permissao permissao);

	void delete(Long id);

	Permissao findById(Long id);

	List<Permissao> findAll();
}
