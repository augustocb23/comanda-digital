package main.domain.relatorios;

import main.domain.enumerator.TipoRelatorio;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface Relatorio {
	static Relatorio gerarRelatorio(@NotNull TipoRelatorio tipo, @NotNull LocalDate dataInicio,
									@NotNull LocalDate dataFim) {
		switch (tipo) {
			case C:
				return new RelatorioConsumo(dataInicio, dataFim);
			case F:
				return new RelatorioFinanceiro(dataInicio, dataFim);
			default:
				throw new NotImplementedException();
		}
	}

	String[] getColunas();

	List<List<String>> getDados();

	static Relatorio getReference() {
		return null;
	}
}
