package main.controller;

import main.domain.Comanda;
import main.persistence.service.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/")
public class ComandaController {
	final private ComandaService comandaService;

	@Autowired
	public ComandaController(ComandaService comandaService) {
		this.comandaService = comandaService;
	}

	@GetMapping("")
	public String funcionarios(ModelMap model) {
		Set<Integer> mesas = comandaService.buscarMesas();
		model.addAttribute("mesas", mesas);
		model.addAttribute("comanda", new Comanda());
		return "/comandas/editar";
	}

	@GetMapping("/mesas")
	public String fragmentMesas(ModelMap model) {
		model.addAttribute("mesas", comandaService.buscarMesas());
		return "/comandas/editar::mesas";
	}

	@GetMapping("/comandas/{mesa}")
	public String fragmentComandas(ModelMap model, @PathVariable String mesa) {
		model.addAttribute("comandas", comandaService.buscarPorMesa(Integer.valueOf(mesa)));
		return "/comandas/editar::comandas";
	}

/*	@GetMapping(value = "/comandas/{mesa}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<Long, String> buscaPorMesa(@PathVariable String mesa) {
		Map<Long, String> longStringMap = comandaService.buscarPorMesa(Integer.valueOf(mesa));
		return longStringMap;
	}*/
}
