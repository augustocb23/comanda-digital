package main.domain.relatorios;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class RelatorioConsumo implements Relatorio {
	RelatorioConsumo(@NotNull LocalDate dataInicio, @NotNull LocalDate dataFim) {
	}
}
