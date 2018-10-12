package main.persistence.service;

import main.domain.Comanda;
import main.domain.enumerator.StatusComanda;
import main.persistence.dao.ComandaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
		Map<Long, String> result = new HashMap<>();
		dao.findAbertasByMesa(mesa).forEach((c) -> result.put(c.getCodigo(), c.getNome()));
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Integer> buscarMesas() {
		Set<Integer> result = new TreeSet<>();
		dao.findByStatus(StatusComanda.A).forEach((c) -> result.add(c.getMesa()));
		return result;
	}

	@Override
	public void editarNomeEMesa(Comanda comanda) {
		dao.setNome(comanda);
		dao.setMesa(comanda);
	}

	@Override
	public void alterarStatus(Comanda comanda) {
		dao.setStatus(comanda);
	}
}
