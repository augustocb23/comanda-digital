package main.persistence.service;

import main.domain.Pedido;

import java.util.List;

public interface PedidoService {
	void salvar(Pedido pedido);

	void editar(Pedido pedido);

	void excluir(Long id);

	Pedido buscarPorId(Long id);

	List<Pedido> buscarTodos();
}
