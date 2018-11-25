package main.domain.relatorios;

import main.BeanUtil;
import main.domain.Produto;
import main.persistence.service.PedidoService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class RelatorioFinanceiro extends AbstractRelatorio {
	private final PedidoService service;
	private Produto produto;

	RelatorioFinanceiro(@NotNull LocalDate dataInicio, @NotNull LocalDate dataFim) {
		super(dataInicio, dataFim, new String[]{"Código", "Produto", "Quant.", "Preço unit", "Total"});
		this.service = BeanUtil.getBean(PedidoService.class);
	}

	@Override
	public List<List<String>> getDados() {
		return new ArrayList<>();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
