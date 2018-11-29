package main.domain.relatorios;

import main.domain.Produto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Model {
	private final Produto produto;
	private final Long quantidade;
	private final Double preco;

	Model(Object[] obj) {
		this.produto = (Produto) obj[0];
		this.quantidade = (Long) obj[1];
		this.preco = (Double) obj[2];
	}

	public List<String> toString(Relatorio.TipoRelatorio tipo) {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.produto.getCodigo()));
		list.add(this.produto.getNome());
		list.add(this.quantidade + (this.produto.getUnidade() != null ? " " + this.produto.getUnidade() : ""));
		switch (tipo) {
			case C:
				list.add(this.produto.getEstoque() != null ?
						this.produto.getEstoque() + " " + this.produto.getUnidade() : null);
				break;
			case F:
				list.add(new DecimalFormat("R$ ##0.00").format(this.produto.getPreco()));
				list.add(new DecimalFormat("R$ ##0.00").format(this.preco));
		}
		return list;
	}
}
