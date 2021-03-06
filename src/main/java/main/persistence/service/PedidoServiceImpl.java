package main.persistence.service;

import main.domain.Pedido;
import main.persistence.dao.PedidoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {
	private final PedidoDao dao;

	@Autowired
	public PedidoServiceImpl(PedidoDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Pedido pedido) {
		dao.save(pedido);
	}

	@Override
	@Transactional
	public void editar(Pedido pedido) {
		dao.update(pedido);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pedido> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public boolean editarStatus(Pedido pedido) {
		Pedido upd = dao.findById(pedido.getCodigo());
		if (pedido.getStatus() == upd.getStatus())
			return false;
		upd.setStatus(pedido.getStatus());
		return true;
	}

	@Override
	public List<Object[]> relatorio(LocalDate inicio, LocalDate fim) {
		return dao.generateReport(inicio, fim);
	}
}
