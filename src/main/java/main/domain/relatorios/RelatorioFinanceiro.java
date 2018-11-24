package main.domain.relatorios;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class RelatorioFinanceiro implements Relatorio {
	RelatorioFinanceiro(@NotNull LocalDate dataInicio, @NotNull LocalDate dataFim) {
	}
}
