package main.domain.relatorios;

import main.domain.enumerator.TipoRelatorio;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractRelatorio implements Relatorio {
	private final TipoRelatorio tipo;
	private final String[] colunas;
	List<Object[]> dados;

	AbstractRelatorio(TipoRelatorio tipo, String[] colunas) {
		this.tipo = tipo;
		this.colunas = colunas;
	}

	public String[] getColunas() {
		return colunas;
	}

	//linhas x colunas
	public List<List<String>> getDados() {
		List<List<String>> result = new ArrayList<>();
		for (Object[] obj : dados) {
			Model relatorio = new Model(obj);
			result.add(relatorio.toString(tipo));
		}
		return result;
	}
}
