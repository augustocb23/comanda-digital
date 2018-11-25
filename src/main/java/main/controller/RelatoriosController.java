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
		model.addAttribute("relatorio", Relatorio.getReference());
		return "relatorios";
	}

	@GetMapping("/gerar")
	public String redirectGerar() {
		return "redirect:/relatorios";
	}

	@PostMapping("/gerar")
	public String gerarRelatorio(@RequestParam(name = "tipo") TipoRelatorio tipo,
								 @RequestParam(name = "inicio") String inicio,
								 @RequestParam(name = "fim") String fim, Model model) {
		LocalDate dataInicio;
		LocalDate dataFim;
		try {
			dataInicio = LocalDate.parse(inicio);
			dataFim = LocalDate.parse(fim);
			model.addAttribute("relatorio", Relatorio.gerarRelatorio(tipo, dataInicio, dataFim));
			//preenche o formulário novamente
			model.addAttribute("tipo", tipo);
			model.addAttribute("inicio", inicio);
			model.addAttribute("fim", fim);
			//verifica se início < fim
			if (dataInicio.isAfter(dataFim)) {
				model.addAttribute("errorTitle", "Data inválida");
				model.addAttribute("errorText", "A data de início deve ser anterior à data de fim");
			}
		} catch (ClassCastException e) {
			model.addAttribute("errorTitle", "Data inválida");
			model.addAttribute("errorText", "A data deve estar no formato YYYY-MM-DD");
			model.addAttribute("relatorio", Relatorio.getReference());
		}
		return "relatorios";
	}

	@ModelAttribute("tipos")
	public TipoRelatorio[] getTipos() {
		return TipoRelatorio.values();
	}
}
