package main.controller;

import main.domain.Funcionario;
import main.domain.Grupo;
import main.persistence.service.FuncionarioService;
import main.persistence.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
	private final FuncionarioService funcionarioService;
	private final GrupoService grupoService;

	@Autowired
	public FuncionarioController(FuncionarioService funcionarioService, GrupoService grupoService) {
		this.funcionarioService = funcionarioService;
		this.grupoService = grupoService;
	}


	@GetMapping("")
	public String funcionarios() {
		return "/funcionarios/funcionarios";
	}

	@GetMapping("/admin")
	public ModelAndView admin() {
		ModelAndView modelAndView = new ModelAndView("/funcionarios/admin");
		modelAndView.addObject("funcionario", funcionarioService.buscarPorLogin("admin"));
		return modelAndView;
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Funcionario funcionario, BindingResult result,
						 RedirectAttributes attributes) {
		//novo
		if (funcionario.getCodigo() != null) {
			funcionarioService.salvar(funcionario);
		}
		//editar
		else {
			funcionarioService.editar(funcionario);
		}

		if (result.hasErrors())
			return "/editar";

		attributes.addFlashAttribute("success", "Funcionário cadastrado");
		return "redirect:/funcionarios/funcionarios";
	}

	@GetMapping("/editar")
	public String editar(ModelMap model) {
		model.addAttribute("funcionario", new Funcionario());
		return "/funcionarios/editar";
	}

	@ModelAttribute("grupos")
	public List<Grupo> listarGrupos() {
		return grupoService.buscarTodos();
	}
}
