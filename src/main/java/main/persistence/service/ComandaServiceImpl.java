package main.persistence.service;

import main.domain.Comanda;
import main.persistence.dao.ComandaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ComandaServiceImpl implements ComandaService {
	private final ComandaDao dao;

	@Autowired
	public ComandaServiceImpl(ComandaDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Comanda comanda) {
		dao.save(comanda);
	}

	@Override
	@Transactional
	public void editar(Comanda comanda) {
		dao.update(comanda);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Comanda buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comanda> buscarTodos() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, String> buscarPorMesa(Integer mesa) {
		return dao.findByTable(mesa);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Integer> buscarMesas() {
		return dao.findTables();
	}
}
