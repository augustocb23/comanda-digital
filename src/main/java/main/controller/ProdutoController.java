package main.controller;

import main.domain.AutoComplete;
import main.domain.Produto;
import main.domain.enumerator.Unidade;
import main.persistence.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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
		return "/produtos/listar";
	}

	@GetMapping("/editar")
	public String editar(ModelMap model) {
		model.addAttribute("produto", new Produto());
		return "/produtos/editar";
	}

	@GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<AutoComplete> autocomplete(@RequestParam String term) {
		return AutoComplete.toList(produtoService.buscarPorNome(term));
	}

	@PostMapping("/editar")
	public String formEditar(@RequestParam(name = "codigo") String codigo, ModelMap model) {
		model.addAttribute("produto", produtoService.buscarPorId(Long.valueOf(codigo)));
		return "produtos/editar";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute Produto produto, RedirectAttributes attributes, BindingResult result,
						 ModelMap model) {
		//verifica se o produto já existe
		if (produtoService.existeProduto(produto.getNome(), produto.getCodigo()))
			result.addError(new FieldError("produto", "nome", "Já existe um produto com o mesmo nome"));
		if (result.hasErrors()) {
			model.addAttribute("produto", produto);
			return "/produtos/editar";
		}

		if (produto.getCodigo() == null) //novo
			produtoService.salvar(produto);
		else //editar
			produtoService.editar(produto);

		attributes.addFlashAttribute("success", "Produto salvo");
		return "redirect:/produtos";
	}

	@GetMapping("/salvar")
	public String salvarRedirect() {
		return "redirect:/produtos/editar";
	}


	@ModelAttribute("unidades")
	public Unidade[] getUnidades() {
		return Unidade.values();
	}
}
