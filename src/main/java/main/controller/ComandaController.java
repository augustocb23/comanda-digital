package main.controller;

import main.domain.Comanda;
import main.domain.enumerator.StatusComanda;
import main.domain.enumerator.StatusPedido;
import main.persistence.service.ComandaService;
import main.security.AuthenticationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("comandas/mesas")
	public String fragmentMesas(ModelMap model) {
		model.addAttribute("mesas", comandaService.buscarMesas());
		return "/comandas/editar::mesas";
	}

	@GetMapping("comandas/mesas/{mesa}")
	public String fragmentComandas(ModelMap model, @PathVariable String mesa) {
		model.addAttribute("comandas", comandaService.buscarPorMesa(Integer.valueOf(mesa)));
		return "/comandas/editar::comandas";
	}

	@GetMapping("/comandas/{comanda}")
	public String fragmentContent(ModelMap model, @PathVariable String comanda) {
		model.addAttribute("comanda", comandaService.buscarPorId(Long.valueOf(comanda)));
		return "/comandas/editar::comanda";
	}

	@GetMapping("/comandas/info/{comanda}")
	public String fragmentInfo(ModelMap model, @PathVariable String comanda) {
		model.addAttribute("comanda", comandaService.buscarPorId(Long.valueOf(comanda)));
		return "/comandas/editar::info";
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
		} else {
			comandaService.editarNomeEMesa(comanda);
		}
		return comanda;
	}

	@PostMapping(value = "/comandas/status", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Comanda finalizaComanda(@RequestBody Comanda comanda) {
		comandaService.alterarStatus(comanda);
		return comanda;
	}

	@GetMapping("/visualizar")
	public String visualizaComandaSelect(ModelMap model) {
		Comanda comanda = new Comanda();
		model.addAttribute("comanda", comanda);
		return "/comandas/visualizar";
	}

	@PostMapping("/visualizar")
	public String visualizaComanda(@RequestParam(name = "codigo") String codigo, RedirectAttributes attributes,
								   @RequestParam(name = "senha", required = false) String senha, ModelMap model) {
		Comanda comanda = comandaService.buscarPorId(Long.valueOf(codigo));
		if (comanda == null) {
			attributes.addFlashAttribute("error", "Comanda não encontrada");
			return "redirect:/visualizar";
		}
		//verifica a senha se não é um funcionário autenticado
		if (!authenticationFacade.isAuthenticated() && comanda.senhaComanda() != Integer.valueOf(senha)) {
			attributes.addFlashAttribute("error", "Senha inválida");
			return "redirect:/visualizar";
		}
		model.addAttribute("comanda", comanda);
		return "/comandas/visualizar";
	}

	@ModelAttribute("statusComanda")
	public StatusComanda[] getStatusComanda() {
		return StatusComanda.values();
	}

	@ModelAttribute("statusPedido")
	public StatusPedido[] getStatusPedido() {
		return StatusPedido.values();
	}

}
