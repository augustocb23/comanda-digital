package main.controller;

import main.domain.Pedido;
import main.domain.enumerator.StatusPedido;
import main.persistence.service.PedidoService;
import main.persistence.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	private final PedidoService pedidoService;
	private final ProdutoService produtoService;

	@Autowired
	public PedidoController(PedidoService pedidoService, ProdutoService produtoService) {
		this.pedidoService = pedidoService;
		this.produtoService = produtoService;
	}

	@PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Pedido salvar(@RequestBody Pedido pedido) {
		//dá baixa no estoque
		produtoService.atualizaEstoque(pedido.getProduto(), pedido.getQuantidade());
		//salva os dados
		pedido.setStatus(StatusPedido.S);
		pedidoService.salvar(pedido);
		return pedido;
	}

	@PostMapping(value = "/status", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Pedido setStatus(@RequestBody Pedido pedido) {
		//verifica se foi alterado e se foi cancelado (retorna ao estoque)
		if (pedidoService.editarStatus(pedido) && pedido.getStatus() == StatusPedido.C) {
			Pedido upd = pedidoService.buscarPorId(pedido.getCodigo());
			produtoService.atualizaEstoque(upd.getProduto(), -upd.getQuantidade());
		}
		return pedido;
	}
}
