package main.persistence.service;

import main.domain.Autorizacao;

import java.util.List;

public interface AutorizacaoService {
	void salvar(Autorizacao autorizacao);

	void editar(Autorizacao autorizacao);

	void excluir(Long id);

	Autorizacao buscarPorId(Long id);

	List<Autorizacao> buscarTodos();
}
