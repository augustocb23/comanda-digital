package main.domain.relatorios;

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

	enum TipoRelatorio {
		C("Consumo"), F("Financeiro");

		private final String desc;

		TipoRelatorio(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	}

	String[] getColunas();

	List<List<String>> getDados();

	static Relatorio getReference() {
		return null;
	}
}
