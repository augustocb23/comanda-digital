package main.persistence.service;

import main.domain.Conversao;

import java.util.List;

public interface ConversaoService {
	void salvar(Conversao conversao);

	void editar(Conversao conversao);

	void excluir(Long id);

	Conversao buscarPorId(Long id);

	List<Conversao> buscarTodos();
}
