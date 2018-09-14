package main.persistence.service;

import main.domain.Comanda;

import java.util.List;

public interface ComandaService {
	void salvar(Comanda comanda);

	void editar(Comanda comanda);

	void excluir(Long id);

	Comanda buscarPorId(Long id);

	List<Comanda> buscarTodos();
}
