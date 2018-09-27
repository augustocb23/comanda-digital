package main.controller;

import main.domain.Funcionario;
import main.domain.Grupo;
import main.persistence.service.FuncionarioService;
import main.persistence.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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
	public String admin(ModelMap model, HttpSession session) {
		Funcionario admin = funcionarioService.buscarPorLogin("admin");
		session.setAttribute("senhaAdmin", Objects.requireNonNull(admin).getSenha());
		session.setAttribute("grupoAdmin", Objects.requireNonNull(admin).getGrupo());
		model.addAttribute("admin", admin);
		return "/funcionarios/admin";
	}

	@PostMapping("/salvarAdmin")
	public String salvarAdmin(@ModelAttribute Funcionario admin, BindingResult result,
							  RedirectAttributes attributes, ModelMap model, HttpSession session) {
		if (!admin.getSenhaNova().isEmpty() && !admin.getSenhaConfirma().isEmpty()) {
			//verifica se as senhas coincidem
			if (!admin.getSenhaNova().contentEquals(admin.getSenhaConfirma()))
				result.addError(new FieldError("senhaNova", "senhaNaoCoincide",
						"Senhas não coincidem"));
			else
				//codifica a nova senha
				admin.encodeSenha(admin.getSenhaNova());
		} else
			//restaura dados padrões a partir da Sessão
			admin.setSenha((String) session.getAttribute("senhaAdmin"));
		admin.setGrupo((Grupo) session.getAttribute("grupoAdmin"));

		if (result.hasErrors()) {
			model.addAttribute("admin", admin);
			return "funcionarios/admin";
		}
		funcionarioService.editar(admin);

		attributes.addFlashAttribute("success", "Administrador alterado");
		return "redirect:/funcionarios/funcionarios";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Funcionario funcionario, BindingResult result,
						 RedirectAttributes attributes, ModelMap model) {
		//verifica se as senhas coincidem
		if (!funcionario.getSenhaNova().contentEquals(funcionario.getSenhaConfirma()))
			result.addError(new FieldError("senhaNova", "senhaNaoCoincide",
					"Senhas não coincidem"));
		else //codifica a senha
			funcionario.encodeSenha(funcionario.getSenhaNova());

		if (result.hasErrors()) {
			model.addAttribute("funcionario", new Funcionario());
			return "funcionarios/editar";
		}

		//novo
		if (funcionario.getCodigo() != null) {
			funcionarioService.salvar(funcionario);
		}
		//editar
		else {
			funcionarioService.editar(funcionario);
		}

		attributes.addFlashAttribute("success", "Funcionário cadastrado");
		return "redirect:/funcionarios";
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
