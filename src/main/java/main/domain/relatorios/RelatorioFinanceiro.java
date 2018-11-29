package main.domain.relatorios;

import main.BeanUtil;
import main.persistence.service.PedidoService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class RelatorioFinanceiro extends AbstractRelatorio {
	RelatorioFinanceiro(@NotNull LocalDate dataInicio, @NotNull LocalDate dataFim) {
		super(TipoRelatorio.F, new String[]{"Código", "Produto", "Quantidade", "Preço unitário", "Total"});
		PedidoService service = BeanUtil.getBean(PedidoService.class);
		this.dados = service.relatorio(dataInicio, dataFim);
	}
}
