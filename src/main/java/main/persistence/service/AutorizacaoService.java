package main.persistence.service;

import main.domain.Permissao;

import java.util.List;

public interface AutorizacaoService {
	void salvar(Permissao permissao);

	void editar(Permissao permissao);

	void excluir(Long id);

	Permissao buscarPorId(Long id);

	List<Permissao> buscarTodos();
}
