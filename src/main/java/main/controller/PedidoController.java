package main.controller;

import main.domain.Pedido;
import main.persistence.service.PedidoService;
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

	@Autowired
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Pedido salvaPedido(@RequestBody Pedido pedido) {
		//todo
		//define os valores padrão
		//pedido.setStatus(StatusPedido.S);
		//salva os dados
		pedidoService.salvar(pedido);
		return pedido;
	}
}
