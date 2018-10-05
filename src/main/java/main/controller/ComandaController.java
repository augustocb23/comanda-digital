package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ComandaController {
	@GetMapping("")
	public String funcionarios(ModelMap model) {
		//model.addAttribute("funcionarios", funcionarioService.buscarTodos());
		return "/comandas/editar";
	}

}
