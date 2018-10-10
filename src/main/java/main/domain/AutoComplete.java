package main.domain;

import java.util.ArrayList;
import java.util.List;

public class AutoComplete {
	private long codigo;
	private String label;

	private AutoComplete(long codigo, String label) {
		this.codigo = codigo;
		this.label = label;
	}

	public static List<AutoComplete> toList(List<Produto> list) {
		List<AutoComplete> result = new ArrayList<>();
		list.forEach((i) -> result.add(new AutoComplete(i.getCodigo(), i.getNome())));
		return result;
	}

	public long getCodigo() {
		return codigo;
	}

	public String getLabel() {
		return label;
	}
}