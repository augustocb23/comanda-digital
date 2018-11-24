package main.controller;

import main.domain.enumerator.TipoRelatorio;
import main.domain.relatorios.Relatorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("relatorios")
public class RelatoriosController {

	@GetMapping("")
	public String listaRelatorios(Model model) {
		model.addAttribute("relatorio");
		return "relatorios/select";
	}

	@GetMapping("/gerar")
	public String redirectGerar() {
		return "redirect:/relatorios";
	}

	@PostMapping("/gerar")
	public String gerarRelatorio(@RequestParam(name = "tipo") TipoRelatorio tipo,
								 @RequestParam(name = "inicio") String inicio,
								 @RequestParam(name = "fim") String fim, Model model) {
		LocalDate dataInicio = LocalDate.parse(inicio);
		LocalDate dataFim = LocalDate.parse(fim);

		model.addAttribute("relatorio", Relatorio.gerarRelatorio(tipo, dataInicio, dataFim));
		return "relatorios/select";
	}

	@ModelAttribute("tipos")
	public TipoRelatorio[] getTipos() {
		return TipoRelatorio.values();
	}

}
