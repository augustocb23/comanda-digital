package main.controller;

import main.domain.Produto;
import main.domain.Unidade;
import main.persistence.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	private final ProdutoService produtoService;

	@Autowired
	public ProdutoController(ProdutoService service) {
		this.produtoService = service;
	}

	@GetMapping("")
	public String funcionarios(ModelMap model) {
		model.addAttribute("produtos", produtoService.buscarTodos());
		return "/produtos/produtos";
	}

	@GetMapping("/editar")
	public String editar(ModelMap model) {
		model.addAttribute("produto", new Produto());
		return "/produtos/editar";
	}

	@PostMapping("/editar")
	public String formEditar(@RequestParam(name = "codigo") String codigo, ModelMap model) {
		model.addAttribute("produto", produtoService.buscarPorId(Long.valueOf(codigo)));
		return "/produtos/editar";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Produto produto) {
		if (produto.getCodigo() == null) //novo
			produtoService.salvar(produto);
		else //editar
			produtoService.editar(produto);
		return "redirect:/produtos";
	}

	@ModelAttribute("unidades")
	public Unidade[] getUnidades() {
		return Unidade.values();
	}
}
