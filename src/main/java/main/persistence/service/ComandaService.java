package main.persistence.service;

import main.domain.Comanda;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ComandaService {
	void salvar(Comanda comanda);

	void editar(Comanda comanda);

	void excluir(Long id);

	Comanda buscarPorId(Long id);

	List<Comanda> buscarTodos();

	Map<Long, String> buscarPorMesa(Integer mesa);

	Set<Integer> buscarMesas();

	void editarNomeEMesa(Comanda comanda);

	void alterarStatus(Comanda comanda);
}
