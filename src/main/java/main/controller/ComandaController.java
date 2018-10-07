package main.controller;

import main.domain.Comanda;
import main.domain.enumerator.StatusComanda;
import main.persistence.service.ComandaService;
import main.security.AuthenticationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/")
public class ComandaController {
	final private AuthenticationFacadeImpl authenticationFacade;
	final private ComandaService comandaService;

	@Autowired
	public ComandaController(ComandaService comandaService, AuthenticationFacadeImpl authenticationFacade) {
		this.comandaService = comandaService;
		this.authenticationFacade = authenticationFacade;
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

	@PostMapping(value = "/comandas/salvar", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Comanda salvaComanda(@RequestBody Comanda comanda) {
		if (comanda.getCodigo() == null) {
			//define os valores padrão
			comanda.setData(new Date());
			comanda.setStatus(StatusComanda.A);
			comanda.setAtendente(authenticationFacade.getFuncionario());
			//salva os dados
			comandaService.salvar(comanda);
		} else
			comandaService.editar(comanda);
		//remove dados do atendente antes de retornar
		comanda.removeAtendente();
		return comanda;
	}
}
