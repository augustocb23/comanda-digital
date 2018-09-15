package main.persistence.dao;

import main.domain.Autorizacao;

import java.util.List;

public interface AutorizacaoDao {
	void save(Autorizacao autorizacao);

	void update(Autorizacao autorizacao);

	void delete(Long id);

	Autorizacao findById(Long id);

	List<Autorizacao> findAll();
}
