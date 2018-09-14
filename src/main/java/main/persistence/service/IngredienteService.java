package main.persistence.service;

import main.domain.Ingrediente;

import java.util.List;

public interface IngredienteService {
	void salvar(Ingrediente ingrediente);

	void editar(Ingrediente ingrediente);

	void excluir(Long id);

	Ingrediente buscarPorId(Long id);

	List<Ingrediente> buscarTodos();
}
