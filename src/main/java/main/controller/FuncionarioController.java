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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
	public String funcionarios(ModelMap model) {
		model.addAttribute("funcionarios", funcionarioService.buscarTodos());
		return "/funcionarios/listar";
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
			//restaura senha padrão a partir da sessão
			admin.setSenha((String) session.getAttribute("senhaAdmin"));
		admin.setGrupo((Grupo) session.getAttribute("grupoAdmin"));

		if (result.hasErrors()) {
			model.addAttribute("admin", admin);
			return "funcionarios/admin";
		}
		funcionarioService.editar(admin);

		attributes.addFlashAttribute("success", "Administrador alterado");
		return "redirect:/funcionarios";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute Funcionario funcionario, BindingResult result,
						 RedirectAttributes attributes, ModelMap model, HttpSession session) {
		if (funcionario.getCodigo() == null) {
			if (funcionario.getSenhaNova().isEmpty())
				result.addError(new FieldError("funcionario", "senhaNova", "Insira a senha"));
			if (funcionario.getSenhaConfirma().isEmpty())
				result.addError(new FieldError("funcionario", "senhaConfirma", "Confirme a senha"));
		}

		if (!funcionario.getSenhaNova().isEmpty() && !funcionario.getSenhaConfirma().isEmpty())
			//verifica se as senhas coincidem
			if (!funcionario.getSenhaNova().contentEquals(funcionario.getSenhaConfirma()))
				result.addError(new FieldError("funcionario", "senhaNova", "Senhas não coincidem"));
			else //codifica a senha
				funcionario.encodeSenha(funcionario.getSenhaNova());
		else //restaura a senha
			funcionario.setSenha((String) session.getAttribute("senha"));

		//verifica se o usuário já existe
		if (funcionarioService.existe(funcionario.getLogin(), funcionario.getCodigo()))
			result.addError(new FieldError("funcionario", "login", "Login já está em uso"));

		if (result.hasErrors()) {
			model.addAttribute("funcionario", funcionario);
			return "funcionarios/editar";
		}

		//novo
		if (funcionario.getCodigo() == null)
			funcionarioService.salvar(funcionario);
		else //editar
			funcionarioService.editar(funcionario);

		attributes.addFlashAttribute("success", "Funcionário cadastrado");
		return "redirect:/funcionarios";
	}

	@GetMapping("/salvar")
	public String redirectEditar() {
		return "redirect:/funcionarios";
	}


	@PostMapping("/editar")
	public String formEditar(@RequestParam(name = "codigo") String codigo, ModelMap model,
							 RedirectAttributes attributes, HttpSession session) {
		if (!codigo.matches("[0-9]+")) {
			attributes.addFlashAttribute("error", "Funcionário inválido");
			return "redirect:/funcionarios";
		}
		Funcionario funcionario = funcionarioService.buscarPorId(Long.valueOf(codigo));
		if (funcionario.getLogin().contentEquals("admin"))
			return "redirect:/funcionarios/admin";
		model.addAttribute("funcionario", funcionario);
		//salva a senha na sessão
		session.setAttribute("senha", Objects.requireNonNull(funcionario).getSenha());
		return "/funcionarios/editar";
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
