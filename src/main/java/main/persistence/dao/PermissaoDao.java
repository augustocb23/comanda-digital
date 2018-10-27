package main.persistence.dao;

import main.domain.Permissao;

import java.util.List;

public interface PermissaoDao {
	List<Permissao> findAll();

	Permissao findById(Long codigo);
}
