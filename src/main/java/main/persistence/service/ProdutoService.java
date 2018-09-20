package main.persistence.service;

import main.domain.Produto;

import java.util.List;

public interface ProdutoService {
	void salvar(Produto produto);

	void editar(Produto produto);

	void excluir(Long id);

	Produto buscarPorId(Long id);

	List<Produto> buscarTodos();
}