package main.controller;

import main.domain.Comanda;
import main.domain.enumerator.StatusComanda;
import main.domain.enumerator.StatusPedido;
import main.persistence.service.ComandaDataTables;
import main.persistence.service.ComandaService;
import main.security.AuthenticationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/")
public class ComandaController {
	final private AuthenticationFacadeImpl authenticationFacade;
	final private ComandaService comandaService;
	final private ComandaDataTables dataTables;

	@Autowired
	public ComandaController(ComandaService comandaService, AuthenticationFacadeImpl authenticationFacade,
							 ComandaDataTables dataTables) {
		this.comandaService = comandaService;
		this.authenticationFacade = authenticationFacade;
		this.dataTables = dataTables;
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

	@GetMapping("/mesas/{mesa}")
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
		//remove dados do atendente antes de retornar o objeto
		comanda.removeAtendente();
		return comanda;
	}

	@PostMapping(value = "/comandas/status", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Comanda finalizaComanda(@RequestBody Comanda comanda) {
		comandaService.alterarStatus(comanda);
		return comanda;
	}

	@GetMapping("/comandas/todas")
	public String todas() {
		return "/comandas/todas";
	}

	@PostMapping(value = "/comandas/todas/lista")
	@ResponseBody
	public DataTablesOutput<Comanda> listaTodas(@Valid @RequestBody DataTablesInput input) {
		return dataTables.findAll(input);
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
