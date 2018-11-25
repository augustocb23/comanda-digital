package main.domain.relatorios;

import main.BeanUtil;
import main.domain.enumerator.TipoRelatorio;
import main.persistence.service.PedidoService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class RelatorioConsumo extends AbstractRelatorio {
	RelatorioConsumo(@NotNull LocalDate dataInicio, @NotNull LocalDate dataFim) {
		super(TipoRelatorio.C, new String[]{"Código", "Produto", "Consumo", "Estoque"});
		PedidoService service = BeanUtil.getBean(PedidoService.class);
		this.dados = service.relatorio(dataInicio, dataFim);
	}
}
