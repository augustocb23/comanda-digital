package main.controller;

import main.domain.Comanda;
import main.domain.enumerator.StatusComanda;
import main.persistence.service.ComandaDataTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/comandas/todas")
public class ComandaTodasController {
	private final ComandaDataTables dataTables;

	@Autowired
	public ComandaTodasController(ComandaDataTables dataTables) {
		this.dataTables = dataTables;
	}

	@GetMapping("")
	public String todas() {
		return "/comandas/todas";
	}

	@PostMapping(value = "/lista")
	@ResponseBody
	public DataTablesOutput<Comanda> listaTodas(@Valid @RequestBody DataTablesInput input) {
		input.getColumns().replaceAll(column -> {
			if ("atendente".equals(column.getData())) {
				column.setData("atendente.login");
			}
			return column;
		});
		return dataTables.findAll(input);
	}

	@ModelAttribute("statusComanda")
	public StatusComanda[] getStatusComanda() {
		return StatusComanda.values();
	}
}
