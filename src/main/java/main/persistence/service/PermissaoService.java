package main.persistence.service;

import main.domain.Permissao;

import java.util.List;

public interface PermissaoService {
	List<Permissao> buscarTodos();

	Permissao buscarPorId(Long valueOf);
}
