package main.controller;

import main.domain.Grupo;
import main.domain.Permissao;
import main.persistence.service.GrupoService;
import main.persistence.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/funcionarios/grupos")
public class GrupoController {
	private final GrupoService grupoService;
	private final PermissaoService permissaoService;

	@Autowired
	public GrupoController(GrupoService grupoService, PermissaoService permissaoService) {
		this.grupoService = grupoService;
		this.permissaoService = permissaoService;
	}

	@GetMapping("")
	public String formGrupos(ModelMap model) {
		model.addAttribute("grupo", new Grupo());
		return "/funcionarios/grupos";
	}

	@PostMapping("")
	public String editarGrupo(@RequestParam(name = "codigo") String codigo, ModelMap model,
							  RedirectAttributes attributes) {
		//busca o grupo
		if (!codigo.matches("[0-9]+")) {
			attributes.addFlashAttribute("error", "Grupo inválido");
			return "redirect:/funcionarios/grupos";
		}
		Grupo grupo = grupoService.buscarPorId(Long.valueOf(codigo));
		//verifica se não é admin ou atendente
		if (grupo.getNome().equals("administrador") || grupo.getNome().equals("atendente"))
			return "redirect:/funcionarios/grupos";
		//cria a string de permissões
		Permissao[] permissoes = new Permissao[10];
		StringBuilder permissoesString = new StringBuilder();
		grupo.getPermissoes().toArray(permissoes);
		for (Permissao permissao : permissoes) {
			if (permissao != null)
				permissoesString.append(',');
			else
				continue;
			permissoesString.append(permissao.getCodigo());
		}
		grupo.setPermissoesString(permissoesString.toString());
		//carrega o grupo e permissoes
		model.addAttribute("grupo", grupo);
		return "/funcionarios/grupos";
	}

	@PostMapping("/salvar")
	public String salvarGrupo(@ModelAttribute Grupo grupo, BindingResult result,
							  RedirectAttributes attributes, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("grupo", grupo);
			return "redirect:/funcionarios/grupos";
		}
		//busca as permissões
		if (grupo.getPermissoesString() != null) {
			Set<Permissao> permissoes = new HashSet<>();
			String[] permissoesString = grupo.getPermissoesString().split(",");
			for (String codigo : permissoesString)
				try {
					permissoes.add(permissaoService.buscarPorId(Long.valueOf(codigo)));
				} catch (Exception ignored) {
				}
			grupo.setPermissoes(permissoes);
		}
		//novo
		if (grupo.getCodigo() == null)
			grupoService.salvar(grupo);
		else //editar
			grupoService.editar(grupo);

		attributes.addFlashAttribute("success", "Grupo cadastrado");
		return "redirect:/funcionarios/grupos";
	}

	@ModelAttribute("grupos")
	public List<Grupo> listarGrupos() {
		return grupoService.buscarTodos();
	}

	@ModelAttribute("permissoes")
	public List<Permissao> listarPermissoes() {
		return permissaoService.buscarTodos();
	}
}
