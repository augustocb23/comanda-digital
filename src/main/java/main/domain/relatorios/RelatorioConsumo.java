package main.domain.relatorios;

import main.BeanUtil;
import main.domain.Produto;
import main.persistence.service.PedidoService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioConsumo extends AbstractRelatorio {
	private final PedidoService service;

	RelatorioConsumo(@NotNull LocalDate dataInicio, @NotNull LocalDate dataFim) {
		super(dataInicio, dataFim, new String[]{"Código", "Produto", "Consumo", "Estoque"});
		this.service = BeanUtil.getBean(PedidoService.class);
	}

	@Override
	public List<List<String>> getDados() {
		List<List<String>> result = new ArrayList<>();
		List<Object[]> listRelatorios = service.relatorioConsumo(dataInicio, dataFim);
		for (Object[] obj : listRelatorios) {
			//faz as conversões para o tipo correto
			Produto produto = (Produto) obj[0];
			Long quantidade = (Long) obj[1];
			List<String> linha = new ArrayList<>();
			//adiciona à lista
			linha.add(String.valueOf(produto.getCodigo()));
			linha.add(produto.getNome());
			linha.add(quantidade + (produto.getUnidade() != null ? " " + produto.getUnidade() : ""));
			linha.add(produto.getEstoque() != null ? produto.getEstoque() + " " + produto.getUnidade() : null);
			result.add(linha);
		}
		return result;
	}
}
