package main.domain.relatorios;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

abstract class AbstractRelatorio implements Relatorio {
	LocalDate dataInicio;
	LocalDate dataFim;
	private String[] colunas;

	AbstractRelatorio(@NotNull LocalDate dataInicio, @NotNull LocalDate dataFim, String[] colunas) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.colunas = colunas;
	}

	public String[] getColunas() {
		return colunas;
	}

	//linhas x colunas
	public abstract List<List<String>> getDados();
}
